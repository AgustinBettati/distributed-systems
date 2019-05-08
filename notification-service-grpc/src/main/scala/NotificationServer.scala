import generated.notification_service.NotificationServiceGrpc
import io.grpc.{ManagedChannelBuilder, ServerBuilder}

import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}


object NotificationServer extends App {
  ProductDatabase.setup()

  val port = Integer.parseInt(args(0))
  val server = ServerBuilder.forPort(port)
    .addService(NotificationServiceGrpc.bindService(new NotificationService(), ExecutionContext.global))
    .build()

  server.start()
  EtcdManager.registerInstanceInEtcd(port, "notification")
  println(s"Running in $port")
  server.awaitTermination()

}
