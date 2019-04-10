import example.hello.{HelloReply, HelloRequest, HelloServiceGrpc}

import scala.concurrent.Future

class HelloService extends HelloServiceGrpc.HelloService {
  override def sayHello(request: HelloRequest): Future[HelloReply] = {

    val reply = HelloReply(message = "Hello " + request.name)
    Future.successful(reply)
  }
}
