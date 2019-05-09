import java.sql.{Connection, Date, DriverManager}

case class NotificationSchema(id: Int, userId: Int, lastNotification: String)

object NotificationDatabase {

  Class.forName("com.mysql.cj.jdbc.Driver")
  val jdbcHostname = "localhost" // si vas a correr localmente
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
          |  id                            bigint auto_increment not null,
          |  userId                          Int,
          |  lastNotification                varchar(255),
          |  constraint pk_notification primary key (id)
          |);
        """.stripMargin)
      connection.createStatement().execute(s"insert into notification (id, userId, lastNotification) values (  1, 10, $dateString);")
      connection.createStatement().execute(s"insert into notification (id, userId, lastNotification) values (  2, 11, $dateString);")
      connection.createStatement().execute(s"insert into notification (id, userId, lastNotification) values (  3, 12, $dateString);")
      connection.createStatement().execute(s"insert into notification (id, userId, lastNotification) values (  4, 13, $dateString);")
    }
  }

  def obtainLastNotification(id: Int): Option[String] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM notification WHERE id = $id")
    Some(resultSet.getString("lastNotification"))
  }

  def setLastNotification(id: Int, date: Date): Unit = {
    val statement = connection.createStatement()
    val dateString = DateUtil.fromDateToString(date)
    val resultSet = statement.executeQuery(s"UPDATE notification SET lastNotification = $dateString WHERE id = $id")
  }

  private def tableExists: Boolean = {
    val dbm = connection.getMetaData
    val tables = dbm.getTables(null, null, "product", null)
    tables.next()
  }
}
