import generated.mail_service.{Empty, MailContent, MailServiceGrpc, Ping}

import scala.concurrent.Future

/**
  * @author Florencia Vimberg
  */

class MailService extends MailServiceGrpc.MailService{
  override def sendMail(request: MailContent): Future[Empty] = {
    println("[MAIL SERVICE] to: " + request.mail + " message: " + request.message)
    Future.successful(
      Empty(

      )
    )
  }

  override def healthCheck(request: Ping): Future[Ping] = Future.successful(Ping())

}
