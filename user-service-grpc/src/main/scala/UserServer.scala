import generated.user.UserServiceGrpc
import io.grpc.ServerBuilder

import scala.concurrent.ExecutionContext

object UserServer extends App {
  UserDatabase.setup()

  val port = 9000
  val server = ServerBuilder.forPort(port)
    .addService(UserServiceGrpc.bindService(new UserService(), ExecutionContext.global))
    .build()

  server.start()
  println(s"Running in $port")
  server.awaitTermination()

}
