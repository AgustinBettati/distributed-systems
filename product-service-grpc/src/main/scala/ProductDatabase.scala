import java.sql.{Connection, DriverManager}

case class ProductSchema(id: Int, name: String, description: String)

object ProductDatabase {

  Class.forName("com.mysql.cj.jdbc.Driver")
//  val jdbcHostname = "localhost" // si vas a correr localmente
  val jdbcHostname = "host.docker.internal" // usar esto si vas a correr con docker
  val jdbcPort = 3306
  val jdbcDatabase = "db-sist"
  val username = "user"
  val password = "pass"
  val jdbcUrl = s"jdbc:mysql://$username:$password@$jdbcHostname:$jdbcPort/$jdbcDatabase"

  val connection: Connection = DriverManager.getConnection(jdbcUrl)

  def setup(): Unit = {
    if(!tableExists) {
      println("Creating product table")
      connection.createStatement().execute(
        """
          |create table product (
          |  id                            bigint auto_increment not null,
          |  name                          varchar(255),
          |  description                   varchar(255),
          |  constraint pk_product primary key (id)
          |);
        """.stripMargin)
      connection.createStatement().execute("insert into product (id,name,description) values (  1,'celular','description');")
      connection.createStatement().execute("insert into product (id,name,description) values (  2,'tele','description');")
      connection.createStatement().execute("insert into product (id,name,description) values (  3,'monitor','description');")
      connection.createStatement().execute("insert into product (id,name,description) values (  4,'reloj','description');")
      connection.createStatement().execute("insert into product (id,name,description) values (  5,'ipad','description');")
    }
  }

  def getAllProducts: List[ProductSchema] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT * FROM product")
    var products: List[ProductSchema] = Nil
    while (resultSet.next()){
      products = ProductSchema(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description")) :: products
    }
    products
  }

  def getProductById(id: Int): Option[ProductSchema] = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM product WHERE id = $id")
    if(resultSet.next()){
      Some(ProductSchema(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description")))
    }
    else None
  }

  private def tableExists: Boolean = {
    val dbm = connection.getMetaData
    val tables = dbm.getTables(null, null, "product", null)
    tables.next()
  }
}
