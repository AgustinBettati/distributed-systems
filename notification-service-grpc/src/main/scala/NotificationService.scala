
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

  //TODO estas referencias no estan funcionando, probar haciendo deployment con cluster ip
  val wishlistChannel: ManagedChannel = ManagedChannelBuilder.forAddress("wishlist-svc", 9000).usePlaintext(true).build()
  val wishlistServiceStub = WishlistServiceGrpc.stub(wishlistChannel)

  val productChannel: ManagedChannel = ManagedChannelBuilder.forAddress("product-svc", 9000).usePlaintext(true).build()
  val productServiceStub = ProductServiceGrpc.stub(productChannel)

  val mailChannel: ManagedChannel = ManagedChannelBuilder.forAddress("mail-svc", 9000).usePlaintext(true).build()
  val mailServiceStub = MailServiceGrpc.stub(mailChannel)


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

    val eventualUser: Future[User] = wishlistServiceStub.getUserWithProducts(UserRequest(request.id))
    eventualUser.flatMap(user => {

      user.productReferences match {
        case Some(references) =>
          val productIds: Seq[Int] = references.id

          val eventualProducts = productIds.map(id => productServiceStub.getProduct(ProductRequest(id)))
          val futureOfProducts: Future[Seq[product_service.Product]] = Future.sequence(eventualProducts)

          futureOfProducts.map(products => {
            NotificationDatabase.setLastNotification(request.id, nowDate)
            mailServiceStub.sendMail(MailContent(user.email, products.mkString(", ")))
            SentMail(s"Sent mail to ${user.email} with the given products: ${products.mkString(", ")}")
          })
        case None =>
          Future.failed(new RuntimeException("no se encontraron referencia de productos"))

      }
    })

  }


  override def healthCheck(request: Ping): Future[Ping] = Future.successful(Ping())

}
