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
      connection.createStatement().execute("insert into notification (id, userId, lastNotification) values (  1, 10, '8-05-19 10:34:09 AM');")
      connection.createStatement().execute("insert into notification (id, userId, lastNotification) values (  2, 11, '6-05-19 10:34:09 AM');")
      connection.createStatement().execute("insert into notification (id, userId, lastNotification) values (  3, 12, '6-05-19 12:34:09 PM');")
      connection.createStatement().execute("insert into notification (id, userId, lastNotification) values (  4, 13, '7-05-19 5:34:09 PM');")
    }
  }

  def userExists(id: Int): Option[NotificationSchema] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM notification WHERE id = $id")
    Some(NotificationSchema(resultSet.getInt("id"), resultSet.getInt("userId"), resultSet.getDate("lastNotification")))
  }

  def obtainLastNotification(id: Int): Option[Date] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM notification WHERE id = $id")
    Some(resultSet.getDate("lastNotification"))
  }

  private def tableExists: Boolean = {
    val dbm = connection.getMetaData
    val tables = dbm.getTables(null, null, "product", null)
    tables.next()
  }
}
