
import java.util.Date

import generated.mail_service.{MailContent, MailServiceGrpc}
import generated.notification_service.{NotificationServiceGrpc, Ping, ProductRequest, SentMail, UserId}
import generated.product_service
import generated.product_service.ProductServiceGrpc
import generated.wishlist.{User, UserRequest, WishlistServiceGrpc}
import io.grpc.{ManagedChannel, ManagedChannelBuilder}

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Florencia Vimberg
  */
class NotificationService extends NotificationServiceGrpc.NotificationService {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  val wishlistServiceWatcher
  : EtcdWatcher[WishlistServiceGrpc.WishlistServiceStub] = new EtcdWatcher(
    "wishlist",
    (port: Integer) => {
      val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build()
      WishlistServiceGrpc.stub(channel)
    }
  )

  val productServiceWatcher
  : EtcdWatcher[ProductServiceGrpc.ProductServiceStub] = new EtcdWatcher(
    "product",
    (port: Integer) => {
      val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build()
      ProductServiceGrpc.stub(channel)
    }
  )

  val mailServiceWatcher
  : EtcdWatcher[MailServiceGrpc.MailServiceStub] = new EtcdWatcher(
    "mail",
    (port: Integer) => {
      val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build()
      MailServiceGrpc.stub(channel)
    }
  )

  override def sendNotification(request: UserId): Future[SentMail] = {
    var nowDate: Date = new Date()
    NotificationDatabase.obtainLastNotification(request.id) match {
      case Some(lastNotification: String) => {
        if (nowDate.getTime - DateUtil.fromStringToDate(lastNotification).getTime() > 10000) {
          sendMail(request, nowDate)
        }
        else {
          Future.failed(new RuntimeException("mail se habia mandado hace poco"))
        }
      }
      case None =>
        sendMail(request, nowDate)
    }
  }

  def sendMail(request: UserId, nowDate: Date): Future[SentMail] = {

    val eventualUser: Future[User] = wishlistServiceWatcher.obtainStub().getUserWithProducts(UserRequest(request.id))
    eventualUser.flatMap(user => {

      user.productReferences match {
        case Some(references) =>
          val productIds: Seq[Int] = references.id

          val eventualProducts = productIds.map(id => productServiceWatcher.obtainStub().getProduct(ProductRequest(id)))
          val futureOfProducts: Future[Seq[product_service.Product]] = Future.sequence(eventualProducts)

          futureOfProducts.map(products => {
            NotificationDatabase.setLastNotification(request.id, nowDate)
            mailServiceWatcher.obtainStub().sendMail(MailContent(user.email, products.mkString(", ")))
            SentMail(products.mkString(", "))
          })
        case None =>
          Future.failed(new RuntimeException("no se encontraron referencia de productos"))

      }
    })

  }


  override def healthCheck(request: Ping): Future[Ping] = Future.successful(Ping())

}
