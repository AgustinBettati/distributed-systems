import generated.product_service.ProductServiceGrpc
import io.grpc.{ManagedChannelBuilder, ServerBuilder}

import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}


object ProductServer extends App {
  ProductDatabase.setup()

  val port = Integer.parseInt(args(0))
  val server = ServerBuilder.forPort(port)
    .addService(ProductServiceGrpc.bindService(new ProductService(), ExecutionContext.global))
    .build()

  server.start()
  EtcdManager.registerInstanceInEtcd(port, "product")
  println(s"Running in $port")
  server.awaitTermination()

}
