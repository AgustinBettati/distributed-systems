

// Generated by Akka gRPC. DO NOT EDIT.
package example.myapp.helloworld.grpc;

import akka.NotUsed;
import akka.Done;
import akka.annotation.*;
import akka.grpc.internal.*;
import akka.grpc.GrpcClientSettings;
import akka.grpc.javadsl.AkkaGrpcClient;
import akka.grpc.javadsl.SingleResponseRequestBuilder;
import akka.grpc.javadsl.StreamResponseRequestBuilder;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.Materializer;

import io.grpc.*;
import io.grpc.stub.*;

import static example.myapp.helloworld.grpc.GreeterService.Serializers.*;


import java.util.concurrent.CompletionStage;
import scala.concurrent.ExecutionContext;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;
import scala.concurrent.Promise;


public abstract class GreeterServiceClient extends GreeterServiceClientPowerApi implements GreeterService, AkkaGrpcClient {
  public static final GreeterServiceClient create(GrpcClientSettings settings, Materializer mat, ExecutionContext ec) {
    return new DefaultGreeterServiceClient(settings, mat, ec);
  }

  protected final static class DefaultGreeterServiceClient extends GreeterServiceClient {

      private final ClientState clientState;
      private final GrpcClientSettings settings;
      private final CallOptions options;
      private final Materializer mat;
      private final ExecutionContext ec;

      private DefaultGreeterServiceClient(GrpcClientSettings settings, Materializer mat, ExecutionContext ec) {
        this.settings = settings;
        this.mat = mat;
        this.ec = ec;
        this.clientState = new ClientState(settings, mat, ec);
        this.options = NettyClientUtils.callOptions(settings);

        if (mat instanceof ActorMaterializer) {
          ((ActorMaterializer) mat).system().getWhenTerminated().whenComplete((v, e) -> close());
        }
      }

  
    
      private final SingleResponseRequestBuilder<example.myapp.helloworld.grpc.HelloRequest, example.myapp.helloworld.grpc.HelloReply> sayHelloRequestBuilder(Future<ManagedChannel> channel){
        return new JavaUnaryRequestBuilder<>(sayHelloDescriptor, channel, options, settings, ec);
      }
    
  

      

        /**
         * For access to method metadata use the parameterless version of sayHello
         */
        public CompletionStage<example.myapp.helloworld.grpc.HelloReply> sayHello(example.myapp.helloworld.grpc.HelloRequest request) {
          return sayHello().invoke(request);
        }

        /**
         * Lower level "lifted" version of the method, giving access to request metadata etc.
         * prefer sayHello(example.myapp.helloworld.grpc.HelloRequest) if possible.
         */
        
          public SingleResponseRequestBuilder<example.myapp.helloworld.grpc.HelloRequest, example.myapp.helloworld.grpc.HelloReply> sayHello()
        
        {
          return clientState.withChannel( this::sayHelloRequestBuilder);
        }
      

      
        private static MethodDescriptor<example.myapp.helloworld.grpc.HelloRequest, example.myapp.helloworld.grpc.HelloReply> sayHelloDescriptor =
          MethodDescriptor.<example.myapp.helloworld.grpc.HelloRequest, example.myapp.helloworld.grpc.HelloReply>newBuilder()
            .setType(
   MethodDescriptor.MethodType.UNARY 
  
  
  
)
            .setFullMethodName(MethodDescriptor.generateFullMethodName("helloworld.GreeterService", "SayHello"))
            .setRequestMarshaller(new ProtoMarshaller<example.myapp.helloworld.grpc.HelloRequest>(HelloRequestSerializer))
            .setResponseMarshaller(new ProtoMarshaller<example.myapp.helloworld.grpc.HelloReply>(HelloReplySerializer))
            .setSampledToLocalTracing(true)
            .build();
        

      /**
       * Initiates a shutdown in which preexisting and new calls are cancelled.
       */
      public CompletionStage<Done> close() {
        return clientState.closeCS() ;
      }

     /**
      * Returns a CompletionState that completes successfully when shutdown via close()
      * or exceptionally if a connection can not be established after maxConnectionAttempts.
      */
      public CompletionStage<Done> closed() {
        return clientState.closedCS();
      }
  }

}


