import java.sql.{Connection, DriverManager}

case class UserSchema(id: Int, name: String)

object UserDatabase {

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
    def setupUser = {
      println("Creating user table")
      connection.createStatement().execute(
        """
          |create table user (
          |  id                            bigint auto_increment not null,
          |  name                          varchar(255),
          |  constraint pk_user primary key (id)
          |);
        """.stripMargin)
      connection.createStatement().execute("insert into user (id,name) values (  10,'gonza');")
      connection.createStatement().execute("insert into user (id,name) values (  20,'apu');")
      connection.createStatement().execute("insert into user (id,name) values (  30,'marcos');")
      connection.createStatement().execute("insert into user (id,name) values (  40,'flor');")
    }
    def setupProductUser = {
      println("Creating product_user table")
      connection.createStatement().execute(
        """
          |create table product_user (
          |  product_id                     bigint not null,
          |  user_id                        bigint not null,
          |  constraint pk_product_user primary key (product_id, user_id)
          |);
        """.stripMargin)
      connection.createStatement().execute("insert into product_user (product_id,user_id) values (  1,10);")
      connection.createStatement().execute("insert into product_user (product_id,user_id) values (  2,10);")
      connection.createStatement().execute("insert into product_user (product_id,user_id) values (  3,10);")
      connection.createStatement().execute("insert into product_user (product_id,user_id) values (  4,10);")
      connection.createStatement().execute("insert into product_user (product_id,user_id) values (  1,20);")
      connection.createStatement().execute("insert into product_user (product_id,user_id) values (  2,20);")
      connection.createStatement().execute("insert into product_user (product_id,user_id) values (  1,30);")
    }

    if(!tableExists) {
      setupUser
      setupProductUser
    }
  }

  def getAllUsers: List[UserSchema] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT * FROM user")
    var users: List[UserSchema] = Nil
    while (resultSet.next()){
      users = UserSchema(resultSet.getInt("id"), resultSet.getString("name")) :: users
    }
    users
  }

  def getUserById(id: Int): Option[UserSchema] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM user WHERE id = $id")
    if(resultSet.next()){
      Some(UserSchema(resultSet.getInt("id"), resultSet.getString("name")))
    }
    else None
  }



  private def tableExists: Boolean = {
    val dbm = connection.getMetaData
    val tables = dbm.getTables(null, null, "user", null)
    tables.next()
  }
}
