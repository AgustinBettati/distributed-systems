import generated.wishlist.WishlistServiceGrpc
import io.grpc.ServerBuilder

import scala.concurrent.ExecutionContext

object WishlistServer extends App {
  WishlistDatabase.setup()

  val port = Integer.parseInt(args(0))
  val server = ServerBuilder.forPort(port)
    .addService(WishlistServiceGrpc.bindService(new WishlistService(), ExecutionContext.global))
    .build()

  server.start()
  EtcdManager.registerInstanceInEtcd(port, "wishlist")
  println(s"Running in $port")
  server.awaitTermination()

}
