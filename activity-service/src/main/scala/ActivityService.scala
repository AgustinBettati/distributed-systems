import java.util.Date

import generated.activity_service.{ActivityServiceGrpc, Ping, UserActivityRequest}
import generated.activity_service
import generated.activity_service._

import scala.concurrent.Future

class ActivityService extends ActivityServiceGrpc.ActivityService {
  override def getUserActivity(request: UserActivityRequest): Future[activity_service.UserActivity] = {
    ActivityDatabase.getUserActivityById(request.id) match {
      case Some(UserActivity(id, date)) =>
        Future.successful(activity_service.UserActivity(id, date))
      case None =>
        Future.failed(new RuntimeException("Not found"))
    }
  }


  override def healthCheck(request: Ping): Future[Ping] = Future.successful(Ping())

  override def getInactiveUsers(request: UsersActivityRequest): Future[UserActivityList] = {
    import java.text.SimpleDateFormat
    val formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa")

    val activities = ActivityDatabase.getAllUserActivities
    val tuples = activities.map(u => (u.id, formatter.parse(u.date)))
    val filter = tuples.filter(tuple => tuple._2.compareTo(new Date()) < -1000)
    val result = filter.map(a => activity_service.UserActivity(a._1, a._2.toString))

    Future.successful(activity_service.UserActivityList(result))
  }

  override def registerActivity(request: activity_service.UserActivity): Future[activity_service.UserActivity] = {
    val value = ActivityDatabase.registerUserActivity(request.id, request.date).get
    Future.successful(activity_service.UserActivity(value.id,value.date))
  }
}

