import generated.mail_service.MailServiceGrpc
import io.grpc.{ServerBuilder}

import scala.concurrent.ExecutionContext

object MailServer extends App {

  val port = 9000
  val server = ServerBuilder.forPort(port)
    .addService(MailServiceGrpc.bindService(new MailService(), ExecutionContext.global))
    .build()

  server.start()
//  EtcdManager.registerInstanceInEtcd(port, "mail")
  println(s"Running in $port")
  server.awaitTermination()

}
