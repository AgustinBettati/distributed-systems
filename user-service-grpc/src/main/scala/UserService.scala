import generated.user._

import scala.concurrent.Future

class UserService extends UserServiceGrpc.UserService {
  /*override def getUser(request: UserRequest): Future[User] = {
    val userReferences = Option(ProductReferences(List(10, 20, 30)))
    Future.successful(User(1, "gonza", userReferences))
  }

  override def getUsers(request: UsersRequest): Future[UserList] = {
    val userReferences = Option(ProductReferences(List(10, 20, 30)))
    Future.successful(
      UserList(
        List(User(1, "gonza", userReferences), User(2, "gonza2", userReferences))
      )
    )
  }*/

  override def getUser(request: UserRequest): Future[User] = {
    UserDatabase.getUserById(request.id) match {
      case Some(UserSchema(id, name)) =>
        Future.successful(User(id, name))
      case None =>
        Future.failed(new RuntimeException("User not found"))
    }
  }

  override def getUsers(request: UsersRequest): Future[UserList] = {
    val users = UserDatabase.getAllUsers
    Future.successful(
      UserList(
        users.map { case UserSchema(id, name) => User(id, name) }
      )
    )
  }
}
