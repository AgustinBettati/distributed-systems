

// Generated by Akka gRPC. DO NOT EDIT.
package example.myapp.helloworld.grpc;

import java.util.concurrent.CompletionStage;

import akka.japi.Function;
import scala.concurrent.Future;

import akka.http.scaladsl.model.HttpRequest;
import akka.http.scaladsl.model.HttpResponse;
import akka.grpc.javadsl.GrpcExceptionHandler;
import akka.actor.ActorSystem;
import akka.stream.Materializer;

import akka.grpc.internal.PlayRouter;

import io.grpc.Status;


  /**
   * Abstract base class for implementing GreeterService in Java and using as a play Router
   */
  public abstract class AbstractGreeterServiceRouter extends PlayRouter implements GreeterService {
    private final scala.Function1<HttpRequest, Future<HttpResponse>> respond;

    public AbstractGreeterServiceRouter(Materializer mat, ActorSystem system) {
      this(mat, system, GrpcExceptionHandler.defaultMapper());
    }

    public AbstractGreeterServiceRouter(Materializer mat, ActorSystem system, Function<ActorSystem, Function<Throwable, Status>> eHandler) {
      super(GreeterService.name);
      this.respond = akka.grpc.internal.PlayRouterHelper.handlerFor(
        GreeterServiceHandlerFactory.create(this, serviceName(), mat, eHandler, system)
      );
    }

    /**
     * INTERNAL API
     */
    public scala.Function1<HttpRequest, Future<HttpResponse>> respond() {
      return respond;
    }
  }

