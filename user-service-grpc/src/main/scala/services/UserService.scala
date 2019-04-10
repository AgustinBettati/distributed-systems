package services

import generated.user._

import scala.concurrent.Future

class UserService extends UserServiceGrpc.UserService {
  override def getUser(request: UserRequest): Future[User] = {
    val productReferences = Option(ProductReferences(List(10, 20, 30)))
    Future.successful(User(1, "gonza", productReferences))
  }

  override def getUsers(request: UsersRequest): Future[UserList] = {
    val productReferences = Option(ProductReferences(List(10, 20, 30)))
    Future.successful(
      UserList(
        List(User(1, "gonza", productReferences), User(2, "gonza2", productReferences))
      )
    )
  }

  override def addProduct(request: ProductUserRequest): Future[User] = {
    val user = new User(1,"user2")
    Future.successful(
      user
    )
  }

  override def deleteProduct(request: ProductUserRequest): Future[User] = {

    val user = new User(1,"user")
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
