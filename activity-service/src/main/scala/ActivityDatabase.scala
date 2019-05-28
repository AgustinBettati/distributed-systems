import java.sql.{Connection, DriverManager}
import java.util.Date

case class UserActivity(id: Int, date: String)

object ActivityDatabase {

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
    if (!tableExists) {
      println("Creating user activity table")
      connection.createStatement().execute(
        """
          |create table userActivity (
          |  id                             bigint auto_increment not null,
          |  date                           varchar(255),
          |  constraint pk_activity primary key (id)
          |);
        """.stripMargin)
      val date = DateUtil.fromDateToString(new Date())
      println("successfully created table")
      connection.createStatement().execute(s"insert into userActivity (id,date) values (  1,'$date');")
      connection.createStatement().execute(s"insert into userActivity (id,date) values (  2,'$date');")
      connection.createStatement().execute(s"insert into userActivity (id,date) values (  3,'$date');")
      connection.createStatement().execute(s"insert into userActivity (id,date) values (  4,'$date');")
      connection.createStatement().execute(s"insert into userActivity (id,date) values (  5,'$date');")
    }
  }

  def getAllUserActivities: List[UserActivity] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT * FROM userActivity")
    var userActivities: List[UserActivity] = Nil
    while (resultSet.next()) {
      userActivities = UserActivity(resultSet.getInt("id"), resultSet.getString("date")) :: userActivities
    }
    userActivities
  }

  def getUserActivityById(id: Int): Option[UserActivity] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM userActivity WHERE id = $id")
    if (resultSet.next()) {
      Some(UserActivity(resultSet.getInt("id"), resultSet.getString("date")))
    }
    else None
  }

  def registerUserActivity(id: Int, date: String): Option[UserActivity] = {
    connection.createStatement().execute(s"delete from userActivity where id=$id;")
    connection.createStatement().execute(s"insert into userActivity (id,date) values (  $id,'$date');")
    getUserActivityById(id)
  }

  private def tableExists: Boolean = {
    val dbm = connection.getMetaData
    val tables = dbm.getTables(null, null, "userActivity", null)
    tables.next()
  }
}
