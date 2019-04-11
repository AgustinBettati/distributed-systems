import generated.user._

import scala.concurrent.Future

class UserService extends UserServiceGrpc.UserService {

  override def getUser(request: UserRequest): Future[User] = {
    UserDatabase.getUserById(request.id) match {
      case Some(UserSchema(id, name, productRefs)) =>
        Future.successful(User(id, name, Option(ProductReferences(productRefs))))
      case None =>
        Future.failed(new RuntimeException("User not found"))
    }
  }

  override def getUsers(request: UsersRequest): Future[UserList] = {
    val users = UserDatabase.getAllUsers
    Future.successful(
      UserList(
        users.map {
          case UserSchema(id, name, productRefs) => User(id, name, Option(ProductReferences(productRefs)))
        }
      )
    )
  }
  override def addProduct(request: ProductUserRequest): Future[ModificationResponse] = {
    Future.successful(ModificationResponse(UserDatabase.addProductToUser(request.idProduct, request.idUser)))
  }

  override def deleteProduct(request: ProductUserRequest): Future[ModificationResponse] = {
    Future.successful(ModificationResponse(UserDatabase.deleteProductFromUser(request.idProduct, request.idUser)))
  }

  override def healthCheck(request: Ping): Future[Ping] = Future.successful(Ping())
}

