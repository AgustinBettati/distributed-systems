import generated.user.UserServiceGrpc
import io.grpc.ServerBuilder

import scala.concurrent.ExecutionContext

object UserServer extends App {
  UserDatabase.setup()

  val port = Integer.parseInt(args(0))
  val server = ServerBuilder.forPort(port)
    .addService(UserServiceGrpc.bindService(new UserService(), ExecutionContext.global))
    .build()

  server.start()
  EtcdManager.registerInstanceInEtcd(port, "wishlist")
  println(s"Running in $port")
  server.awaitTermination()

}
