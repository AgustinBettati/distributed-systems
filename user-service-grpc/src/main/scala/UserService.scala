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

  override def addProduct(request: ProductUserRequest): Future[User] = {
    val user = new User(1, "user2")
    Future.successful(
      user
    )
  }

  override def deleteProduct(request: ProductUserRequest): Future[User] = {
    val user = new User(1, "user")
    Future.successful(
      user
    )
  }

  override def getProductsOfUser(request: UserRequest): Future[ProductReferences] = {
    val pr = new ProductReferences()
    Future.successful(
      pr
    )
  }
}

