
import java.util.concurrent.atomic.AtomicInteger

import generated.product_service.Ping
import generated.product_service.ProductServiceGrpc.ProductServiceStub
import generated.user.UserServiceGrpc.UserServiceStub
import scala.concurrent._
import scala.util.{Failure, Success}
import scala.concurrent.duration._

object LoadBalancers {
  case class UserBalancer(stubs: List[(UserServiceStub, Int)]) {
    def obtainStub(implicit executor: ExecutionContext): UserServiceStub = {
      val futures: List[Future[(UserServiceStub, Int)]] = stubs.map { case (stub, port) => stub.healthCheck(Ping()).map(_ => (stub, port)) }
      val bestStub: (UserServiceStub, Int) = Await.result(firstSucceededOf(futures), 1500 millis)
      println(s"\n[load balancer] The stub in port ${bestStub._2} was selected for the request\n")
      bestStub._1
    }
  }

  case class ProductBalancer(stubs: List[(ProductServiceStub, Int)]) {
    def obtainStub(implicit executor: ExecutionContext): ProductServiceStub = {
      val futures = stubs.map { case (stub, port) => stub.healthCheck(Ping()).map(_ => (stub, port)) }
      val bestStub = Await.result(firstSucceededOf(futures), 1500 millis)
      println(s"\n[load balancer] The stub in port ${bestStub._2} was selected for the request\n")
      bestStub._1
    }
  }

  private def firstSucceededOf[Z](futures: List[Future[Z]])(implicit executor: ExecutionContext): Future[Z] = {
    val p = Promise[Z]()
    val size = futures.size
    val failureCount = new AtomicInteger(0)
    futures foreach {
      _.onComplete {
        case Success(v) => p.trySuccess(v)
        case Failure(e) =>
          val count = failureCount.incrementAndGet
          if (count == size) p.tryFailure(e)
      }
    }
    p.future
  }
}
