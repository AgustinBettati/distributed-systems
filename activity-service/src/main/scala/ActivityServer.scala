import generated.activity_service.ActivityServiceGrpc

import io.grpc.{ManagedChannelBuilder, ServerBuilder}
import scala.concurrent.ExecutionContext


object ActivityServer extends App {
  ActivityDatabase.setup()

  val port = 9000
  val server = ServerBuilder.forPort(port)
    .addService(ActivityServiceGrpc.bindService(new ActivityService(), ExecutionContext.global))
    .build()

  server.start()
  println(s"Running in $port")
  server.awaitTermination()

}
