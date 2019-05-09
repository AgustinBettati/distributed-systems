import java.sql.Date

import generated.mail_service.{MailContent, MailServiceGrpc}
import generated.notification_service.{NotificationServiceGrpc, Ping, ProductRequest, SentMail, UserId}
import generated.product_service.ProductServiceGrpc
import generated.wishlist.{UserRequest, WishlistServiceGrpc}
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
    NotificationDatabase.obtainLastNotification(request.id){
      //case encuentra una fecha vieja -> manda mail (primero wishlist, desp product service ultimo mail service)
      case Some(lastNotification: String) => {
        if ((DateUtil.fromStringToDate(lastNotification).getTime() - nowDate.getTime()) > 30000) {
          sendMail(request, nowDate)
        }
        //case encuentra una fecha reciente -> no hace nada
      }
      case None =>
        sendMail(request, nowDate)
        NotificationDatabase.setLastNotification(request.id, nowDate)
        //mandarle mail -> mando mail idem fecha vieja. Guardo en la base de datos nueva fecha de mail
    }
  }

  def sendMail(request: UserId, nowDate: Date): Unit = {
    wishlistServiceWatcher.obtainStub().getUserWithProducts(UserRequest(request.id)).map(user => {
      user.productReferences.map(products => {
        val productId = products.id.head

        productServiceWatcher.obtainStub().getProduct(ProductRequest(productId)).map(product => {
          mailServiceWatcher.obtainStub().sendMail(MailContent(user.email, product.name + product.description))
        })
      })
    })

    NotificationDatabase.setLastNotification(request.id, nowDate)
  }

  override def healthCheck(request: Ping): Future[Ping] = Future.successful(Ping())

}
