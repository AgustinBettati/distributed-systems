import example.hello.{HelloReply, HelloRequest, HelloServiceGrpc}
import generated.product_service.ProductServiceGrpc
import io.grpc.netty.NettyServerBuilder
import io.grpc.{ManagedChannelBuilder, ServerBuilder}
import services.ProductService

import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


object ProductServer extends App {

//  val db: JdbcBackend.DatabaseDef = slick.jdbc.JdbcBackend.Database.forURL("mysql://user:pass@localhost:3306/db-sist", driver="com.mysql.cj.jdbc.Driver")

  val port = 9000
  val server = ServerBuilder.forPort(port)
    .addService(ProductServiceGrpc.bindService(new ProductService(), ExecutionContext.global))
    .build()

  server.start()
  println(s"Running in $port")
  server.awaitTermination()

}
