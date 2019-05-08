import generated.mail_service.MailServiceGrpc
import io.grpc.{ServerBuilder}

import scala.concurrent.ExecutionContext

object MailServer extends App {

  val port = Integer.parseInt(args(0))
  val server = ServerBuilder.forPort(port)
    .addService(MailServiceGrpc.bindService(new MailService(), ExecutionContext.global))
    .build()

  server.start()
  EtcdManager.registerInstanceInEtcd(port, "mail")
  println(s"Running in $port")
  server.awaitTermination()

}
