import generated.product_service.ProductServiceGrpc.ProductServiceStub
import generated.product_service.{ProductList, ProductRequest, ProductServiceGrpc, ProductsRequest}
import generated.user.UserServiceGrpc.UserServiceStub
import generated.user.{ProductUserRequest, UserRequest, UserServiceGrpc, UsersRequest}
import io.grpc.{ManagedChannel, ManagedChannelBuilder, ServerBuilder}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}


object ClientDemo extends App {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  def obtainServiceBalancer[T](channelToStub: ManagedChannel => T, ports: List[Int]): ClientBalancer[T] = {
    val stubs = ports.map {
      port => channelToStub(ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build())
    }
    ClientBalancer(stubs)
  }

  private val productServiceBalancer = obtainServiceBalancer((channel: ManagedChannel) => ProductServiceGrpc.stub(channel), List(9000, 9001))
  private val userServiceBalancer = obtainServiceBalancer((channel: ManagedChannel) => UserServiceGrpc.stub(channel), List(8000, 8001))


  val users = userServiceBalancer.obtainStub.getUsers(UsersRequest())
  users.onComplete {
    case Success(value) =>
      print("\nUsers with their references: \n")
      value.users.foreach(u =>
        print(u.name + "\t\t" + u.productReferences.get.id.mkString(", ") + "\n")
      )

      val products: Future[ProductList] = productServiceBalancer.obtainStub.getProducts(ProductsRequest())
      products.onComplete {
        case Success(productsResp) =>
          print("\nProducts: \n")

          for (elem <- productsResp.product) {
            print(elem.id + " ----> " + elem.name + " , " + elem.description + "\n")
          }
          print("\n\nAdding product 1 to flor...\n")
          userServiceBalancer.obtainStub.addProduct(ProductUserRequest(40, 1)).onComplete {
            case Success(_) =>
              print("Product added!\n")
              userServiceBalancer.obtainStub.getUser(UserRequest(40)).onComplete {
                case Success(user) =>
                  print(user.name + "\t\t" + user.productReferences.get.id.mkString(", ") + "\n")
                  userServiceBalancer.obtainStub.deleteProduct(ProductUserRequest(40, 1))
                case Failure(exception) => print(exception)
              }
            case Failure(exception) => print(exception)
          }
        case Failure(exception) => print(exception)
      }
    case Failure(exception)
    => print(exception)
  }

  System.in.read()
}