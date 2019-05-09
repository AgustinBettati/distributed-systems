import generated.wishlist._

import scala.concurrent.Future

class WishlistService extends WishlistServiceGrpc.WishlistService {

  override def getUserWithProducts(request: UserRequest): Future[User] = {
    WishlistDatabase.getUserById(request.id) match {
      case Some(UserSchema(id, name, productRefs)) =>
        Future.successful(User(id, name, Option(ProductReferences(productRefs))))
      case None =>
        Future.failed(new RuntimeException("User not found"))
    }
  }

  override def getUsers(request: UsersRequest): Future[UserList] = {
    val users = WishlistDatabase.getAllUsers
    Future.successful(
      UserList(
        users.map {
          case UserSchema(id, name, productRefs) => User(id, name, Option(ProductReferences(productRefs)))
        }
      )
    )
  }
  override def addProduct(request: ProductUserRequest): Future[ModificationResponse] = {
    Future.successful(ModificationResponse(WishlistDatabase.addProductToUser(request.idProduct, request.idUser)))
  }

  override def deleteProduct(request: ProductUserRequest): Future[ModificationResponse] = {
    Future.successful(ModificationResponse(WishlistDatabase.deleteProductFromUser(request.idProduct, request.idUser)))
  }

  override def healthCheck(request: Ping): Future[Ping] = Future.successful(Ping())
}

