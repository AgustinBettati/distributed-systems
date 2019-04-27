import generated.product_service.{ProductList, ProductRequest, ProductServiceGrpc, ProductsRequest}
import generated.user.{ProductUserRequest, UserRequest, UserServiceGrpc, UsersRequest}
import io.grpc.stub.AbstractStub
import io.grpc.{ManagedChannel, ManagedChannelBuilder}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}


object ClientDemo extends App {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global


  val productsWatcher: EtcdWatcher[ProductServiceGrpc.ProductServiceStub] = new EtcdWatcher("product", (port: Integer) => {
    val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", port)
      .usePlaintext(true)
      .build()
    ProductServiceGrpc.stub(channel)
  })

//  val users = userServiceBalancer.obtainStub.getUsers(UsersRequest())
//  users.onComplete {
//    case Success(value) =>
//      print("\nUsers with their references: \n")
//      value.users.foreach(u =>
//        print(u.name + "\t\t" + u.productReferences.get.id.mkString(", ") + "\n")
//      )

      val products: Future[ProductList] = productsWatcher.obtainStub.getProducts(ProductsRequest())
      products.onComplete {
        case Success(productsResp) =>
          print("\nProducts: \n")

          for (elem <- productsResp.product) {
            print(elem.id + " ----> " + elem.name + " , " + elem.description + "\n")
          }
//          print("\n\nAdding product 1 to flor...\n")
//          userServiceBalancer.obtainStub.addProduct(ProductUserRequest(40, 1)).onComplete {
//            case Success(_) =>
//              print("Product added!\n")
//              userServiceBalancer.obtainStub.getUser(UserRequest(40)).onComplete {
//                case Success(user) =>
//                  print(user.name + "\t\t" + user.productReferences.get.id.mkString(", ") + "\n")
//                  userServiceBalancer.obtainStub.deleteProduct(ProductUserRequest(40, 1))
//                case Failure(exception) => print(exception)
//              }
//            case Failure(exception) => print("failed to add product to user")
//          }
        case Failure(exception) => print("failed to obtain products")
      }
//    case Failure(exception) => print("failed to obtain users")
//  }

  System.in.read()
}