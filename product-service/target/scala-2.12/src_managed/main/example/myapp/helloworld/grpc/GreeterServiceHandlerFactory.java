

// Generated by Akka gRPC. DO NOT EDIT.
package example.myapp.helloworld.grpc;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import akka.japi.Function;
import akka.http.javadsl.model.*;
import akka.actor.ActorSystem;
import akka.stream.Materializer;

import akka.grpc.Codec;
import akka.grpc.Codecs;
import akka.grpc.ProtobufSerializer;
import akka.grpc.javadsl.GoogleProtobufSerializer;
import akka.grpc.javadsl.GrpcMarshalling;
import akka.grpc.javadsl.GrpcExceptionHandler;
import akka.grpc.javadsl.Metadata;
import akka.grpc.javadsl.MetadataImpl;
import akka.grpc.javadsl.package$;

import io.grpc.Status;

import static example.myapp.helloworld.grpc.GreeterService.Serializers.*;


  public class GreeterServiceHandlerFactory {

    private static final CompletionStage<HttpResponse> notFound = CompletableFuture.completedFuture(
      HttpResponse.create().withStatus(StatusCodes.NOT_FOUND));

    /**
     * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example `Http().bindAndHandleAsync`
     * for the generated partial function handler and ends with `StatusCodes.NotFound` if the request is not matching.
     *
     * Use `akka.grpc.scaladsl.ServiceHandler.concatOrNotFound` with `GreeterServiceHandler.partial` when combining
     * several services.
     */
    public static Function<HttpRequest, CompletionStage<HttpResponse>> create(GreeterService implementation, Materializer mat, ActorSystem system) {
      return create(implementation, GreeterService.name, mat, system);
    }

    /**
     * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example `Http().bindAndHandleAsync`
     * for the generated partial function handler and ends with `StatusCodes.NotFound` if the request is not matching.
     *
     * Use `akka.grpc.scaladsl.ServiceHandler.concatOrNotFound` with `GreeterServiceHandler.partial` when combining
     * several services.
     */
    public static Function<HttpRequest, CompletionStage<HttpResponse>> create(GreeterService implementation, Materializer mat, Function<ActorSystem, Function<Throwable, Status>> eHandler, ActorSystem system) {
      return create(implementation, GreeterService.name, mat, eHandler, system);
    }

    /**
     * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example `Http().bindAndHandleAsync`
     * for the generated partial function handler and ends with `StatusCodes.NotFound` if the request is not matching.
     *
     * Use `akka.grpc.scaladsl.ServiceHandler.concatOrNotFound` with `GreeterServiceHandler.partial` when combining
     * several services.
     *
     * Registering a gRPC service under a custom prefix is not widely supported and strongly discouraged by the specification.
     */
    public static Function<HttpRequest, CompletionStage<HttpResponse>> create(GreeterService implementation, String prefix, Materializer mat, ActorSystem system) {
      return partial(implementation, prefix, mat, GrpcExceptionHandler.defaultMapper(), system);
    }

    /**
     * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example `Http().bindAndHandleAsync`
     * for the generated partial function handler and ends with `StatusCodes.NotFound` if the request is not matching.
     *
     * Use `akka.grpc.scaladsl.ServiceHandler.concatOrNotFound` with `GreeterServiceHandler.partial` when combining
     * several services.
     *
     * Registering a gRPC service under a custom prefix is not widely supported and strongly discouraged by the specification.
     */
    public static Function<HttpRequest, CompletionStage<HttpResponse>> create(GreeterService implementation, String prefix, Materializer mat, Function<ActorSystem, Function<Throwable, Status>> eHandler, ActorSystem system) {
      return partial(implementation, prefix, mat, eHandler, system);
    }

    /**
     * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example
     * `Http.get(system).bindAndHandleAsync`. It ends with `StatusCodes.NotFound` if the request is not matching.
     *
     * Use `akka.grpc.javadsl.ServiceHandler.concatOrNotFound` when combining several services.
     */
    public static Function<HttpRequest, CompletionStage<HttpResponse>> partial(GreeterService implementation, String prefix, Materializer mat, ActorSystem system) {
      return partial(implementation, prefix, mat, GrpcExceptionHandler.defaultMapper(), system);
    }

    /**
     * Creates a `HttpRequest` to `HttpResponse` handler that can be used in for example
     * `Http.get(system).bindAndHandleAsync`. It ends with `StatusCodes.NotFound` if the request is not matching.
     *
     * Use `akka.grpc.javadsl.ServiceHandler.concatOrNotFound` when combining several services.
     */
    public static Function<HttpRequest, CompletionStage<HttpResponse>> partial(GreeterService implementation, String prefix, Materializer mat, Function<ActorSystem, Function<Throwable, Status>> eHandler, ActorSystem system) {
      return (req -> {
        Iterator<String> segments = req.getUri().pathSegments().iterator();
        if (segments.hasNext() && segments.next().equals(prefix) && segments.hasNext()) {
          String method = segments.next();
          if (segments.hasNext()) return notFound; // we don't allow any random `/prefix/Method/anything/here
          else return handle(req, method, implementation, mat, eHandler, system).exceptionally(e -> GrpcExceptionHandler.standard(e, eHandler, system));
        } else {
          return notFound;
        }
      });
    }

      public String getServiceName() {
        return GreeterService.name;
      }

    private static CompletionStage<HttpResponse> handle(HttpRequest request, String method, GreeterService implementation, Materializer mat, Function<ActorSystem, Function<Throwable, Status>> eHandler, ActorSystem system) {
      Codec responseCodec = Codecs.negotiate(request);
      
      switch(method) {
        
        case "SayHello":
          return GrpcMarshalling.unmarshal(request, HelloRequestSerializer, mat)
            .thenCompose(e -> implementation.sayHello(e))
            .thenApply(e -> GrpcMarshalling.marshal(e, HelloReplySerializer, mat, responseCodec, system, package$.MODULE$.scalaAnonymousPartialFunction(eHandler)));
        
        default:
          CompletableFuture<HttpResponse> result = new CompletableFuture<>();
          result.completeExceptionally(new UnsupportedOperationException("Not implemented: " + method));
          return result;
      }
    }
  }

