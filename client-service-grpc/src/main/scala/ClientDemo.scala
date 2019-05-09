import generated.activity_service.{ActivityServiceGrpc, UserActivityRequest, UsersActivityRequest}
import generated.product_service.{ProductList, ProductServiceGrpc, ProductsRequest}
import generated.wishlist.{ProductUserRequest, UserRequest, UsersRequest, WishlistServiceGrpc}
import io.grpc.stub.AbstractStub
import io.grpc.{ManagedChannel, ManagedChannelBuilder}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object ClientDemo extends App {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  val productServiceWatcher
    : EtcdWatcher[ProductServiceGrpc.ProductServiceStub] = new EtcdWatcher(
    "product",
    (port: Integer) => {
      val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build()
      ProductServiceGrpc.stub(channel)
    }
  )

  val activityServiceWatcher
    : EtcdWatcher[ActivityServiceGrpc.ActivityServiceStub] = new EtcdWatcher(
    "activity",
    (port: Integer) => {
      val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build()
      ActivityServiceGrpc.stub(channel)
    }
  )

  val wishListServiceWatcher: EtcdWatcher[WishlistServiceGrpc.WishlistServiceStub] =
    new EtcdWatcher(
      "wishlist",
      (port: Integer) => {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build()
        WishlistServiceGrpc.stub(channel)
      }
    )

  activityServiceWatcher
    .obtainStub()
    .getInactiveUsers(UsersActivityRequest())
    .onComplete {
      case Success(userList) =>
        println("Listing inactive users id: ")
        println(userList.users)
        println("\nRegistering activity in user with id 1")
        activityServiceWatcher.obtainStub().registerActivity(UserActivityRequest(1)).onComplete { _ =>
            println("\nListing inactive users id: ")
            activityServiceWatcher.obtainStub().getInactiveUsers(UsersActivityRequest()).onComplete {
                case Success(newUserList) =>
                  println(newUserList.users)
                case _ =>
              }
          }
      case Failure(_) =>
    }

  Thread.sleep(2000)

  println("\n" * 3)

  val users = wishListServiceWatcher.obtainStub.getUsers(UsersRequest())
  users.onComplete {
    case Success(value) =>
      print("\nUsers with their references: \n")
      value.users.foreach(
        u =>
          print(
            u.email + "\t\t" + u.productReferences.get.id.mkString(", ") + "\n"))

      val products: Future[ProductList] =
        productServiceWatcher.obtainStub.getProducts(ProductsRequest())
      products.onComplete {
        case Success(productsResp) =>
          print("Products: \n")

          for (elem <- productsResp.product) {
            print(
              elem.id + " ----> " + elem.name + " , " + elem.description + "\n")
          }
          print("\n\nAdding product 1 to flor...\n")
          wishListServiceWatcher.obtainStub.addProduct(ProductUserRequest(40, 1)).onComplete {
              case Success(_) =>
                print("Product added!\n")
                wishListServiceWatcher.obtainStub.getUserWithProducts(UserRequest(40)).onComplete {
                    case Success(user) =>
                      print(user.email + "\t\t" + user.productReferences.get.id
                        .mkString(", ") + "\n")
                      wishListServiceWatcher.obtainStub.deleteProduct(
                        ProductUserRequest(40, 1))
                    case Failure(exception) => print(exception)
                  }
              case Failure(exception) => print("failed to add product to user")
            }
        case Failure(exception) => print("failed to obtain products")
      }
    case Failure(exception) => print("failed to obtain users")
  }

  System.in.read()
}
