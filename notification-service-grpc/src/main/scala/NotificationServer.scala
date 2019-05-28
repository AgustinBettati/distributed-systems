import java.util.Date

import EtcdManager.leaseClient
import akka.actor.ActorSystem
import generated.activity_service.{ActivityServiceGrpc, UserActivity, UsersActivityRequest}
import generated.notification_service.{NotificationServiceGrpc, UserId}
import io.grpc.{ManagedChannel, ManagedChannelBuilder, ServerBuilder}
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global




object NotificationServer extends App {
  NotificationDatabase.setup()

  val port = 9000
  val server = ServerBuilder.forPort(port)
    .addService(NotificationServiceGrpc.bindService(new NotificationService(), ExecutionContext.global))
    .build()

  server.start()
//  EtcdManager.askForMaster(port, sendNotifications)
//  EtcdManager.registerInstanceInEtcd(port, "notification")
  println(s"Running in $port")
  server.awaitTermination()


//  private def sendNotifications() = {
//    val activityServiceWatcher
//    : EtcdWatcher[ActivityServiceGrpc.ActivityServiceStub] = new EtcdWatcher(
//      "activity",
//      (port: Integer) => {
//        val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build()
//        ActivityServiceGrpc.stub(channel)
//      }
//    )
//    val notificationServiceWatcher
//    : EtcdWatcher[NotificationServiceGrpc.NotificationServiceStub] = new EtcdWatcher(
//      "notification",
//      (port: Integer) => {
//        val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext(true).build()
//        NotificationServiceGrpc.stub(channel)
//      }
//    )
//    val system = ActorSystem("mySystem")
//    system.scheduler.schedule(0 seconds, 3 seconds) {
//
//      println(s"From port $port (notification service master), looking for inactive users")
//      activityServiceWatcher.obtainStub().getInactiveUsers(UsersActivityRequest()).map(list => {
//        println(s"lista de inactivos: ${list.users}")
//        list.users.map {case UserActivity(id, date) => notificationServiceWatcher.obtainStub().sendNotification(UserId(id))}
//      })
//    }
//  }

}
