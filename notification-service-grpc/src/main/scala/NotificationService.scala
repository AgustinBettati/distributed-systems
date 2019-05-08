import generated.notification_service.{MailContent, NotificationServiceGrpc, Ping, UserId}

import scala.concurrent.Future

/**
  * @author Florencia Vimberg
  */
class NotificationService extends NotificationServiceGrpc.NotificationService {
  override def sendNotification(request: UserId): Future[MailContent] = {
//    NotificationDatabase.userExists(request.id) match {
//      case Some(NotificationSchema(id,userId, lastNotification)) =>
//        Future.successful(MailContent("to: " + userId + " message: check our new product"))
//      case None =>
//        Future.failed(new RuntimeException("Not found"))
//    }
    NotificationDatabase.obtainLastNotification(request.id){
      case None =>
        //mandarle notif
    }
  }

  override def healthCheck(request: Ping): Future[Ping] = Future.successful(Ping())

}
