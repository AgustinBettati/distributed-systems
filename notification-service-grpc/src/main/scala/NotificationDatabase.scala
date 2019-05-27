import java.sql.{Connection, DriverManager}
import java.util.Date

case class NotificationSchema(id: Int, userId: Int, lastNotification: String)

object NotificationDatabase {

  Class.forName("com.mysql.cj.jdbc.Driver")
//  val jdbcHostname = "localhost" // si vas a correr localmente
  val jdbcHostname = "mysql" // si vas a correr en kubernetes
  //  val jdbcHostname = "host.docker.internal" // usar esto si vas a correr con docker
  val jdbcPort = 3306
  val jdbcDatabase = "db-sist"
  val username = "user"
  val password = "pass"
  val jdbcUrl = s"jdbc:mysql://$username:$password@$jdbcHostname:$jdbcPort/$jdbcDatabase"

  val connection: Connection = DriverManager.getConnection(jdbcUrl)

  def setup(): Unit = {
    val date = new Date()
    val dateString = DateUtil.fromDateToString(date)
    if(!tableExists) {
      println("Creating notification table")
      connection.createStatement().execute(
        """
          |create table notification (
          |  userId                          bigint auto_increment not null,
          |  lastNotification                varchar(255),
          |  constraint pk_notification primary key (userId)
          |);
        """.stripMargin)
      connection.createStatement().execute(s"insert into notification (userId, lastNotification) values (  1, '$dateString');")
      connection.createStatement().execute(s"insert into notification (userId, lastNotification) values (  2, '$dateString');")
      connection.createStatement().execute(s"insert into notification (userId, lastNotification) values (  3, '$dateString');")
      connection.createStatement().execute(s"insert into notification (userId, lastNotification) values (  4, '$dateString');")
      connection.createStatement().execute(s"insert into notification (userId, lastNotification) values (  5, '$dateString');")
    }
  }

  def obtainLastNotification(id: Int): Option[String] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM notification WHERE userId = $id")
    if (resultSet.next()) {
      Some(resultSet.getString("lastNotification"))
    }
    else None
  }

  def setLastNotification(id: Int, date: Date): Unit = {
    val stringDate = DateUtil.fromDateToString(date)
    connection.createStatement().execute(s"delete from notification where userId=$id;")
    connection.createStatement().execute(s"insert into notification (userId,lastNotification) values (  $id,'$stringDate');")
  }

  private def tableExists: Boolean = {
    val dbm = connection.getMetaData
    val tables = dbm.getTables(null, null, "notification", null)
    tables.next()
  }
}
