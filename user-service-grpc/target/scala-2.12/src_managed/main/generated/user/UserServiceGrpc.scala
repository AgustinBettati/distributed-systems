package generated.user

object UserServiceGrpc {
  val METHOD_GET_USER: _root_.io.grpc.MethodDescriptor[generated.user.UserRequest, generated.user.User] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("UserService", "GetUser"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.UserRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.User])
      .build()
  
  val METHOD_GET_USERS: _root_.io.grpc.MethodDescriptor[generated.user.UsersRequest, generated.user.UserList] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("UserService", "GetUsers"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.UsersRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.UserList])
      .build()
  
  val METHOD_ADD_PRODUCT: _root_.io.grpc.MethodDescriptor[generated.user.ProductUserRequest, generated.user.User] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("UserService", "AddProduct"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.ProductUserRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.User])
      .build()
  
  val METHOD_DELETE_PRODUCT: _root_.io.grpc.MethodDescriptor[generated.user.ProductUserRequest, generated.user.User] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("UserService", "DeleteProduct"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.ProductUserRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.User])
      .build()
  
  val METHOD_GET_PRODUCTS_OF_USER: _root_.io.grpc.MethodDescriptor[generated.user.UserRequest, generated.user.ProductReferences] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("UserService", "GetProductsOfUser"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.UserRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[generated.user.ProductReferences])
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("UserService")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(generated.user.UserProto.javaDescriptor))
      .addMethod(METHOD_GET_USER)
      .addMethod(METHOD_GET_USERS)
      .addMethod(METHOD_ADD_PRODUCT)
      .addMethod(METHOD_DELETE_PRODUCT)
      .addMethod(METHOD_GET_PRODUCTS_OF_USER)
      .build()
  
  trait UserService extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = UserService
    def getUser(request: generated.user.UserRequest): scala.concurrent.Future[generated.user.User]
    def getUsers(request: generated.user.UsersRequest): scala.concurrent.Future[generated.user.UserList]
    def addProduct(request: generated.user.ProductUserRequest): scala.concurrent.Future[generated.user.User]
    def deleteProduct(request: generated.user.ProductUserRequest): scala.concurrent.Future[generated.user.User]
    def getProductsOfUser(request: generated.user.UserRequest): scala.concurrent.Future[generated.user.ProductReferences]
  }
  
  object UserService extends _root_.scalapb.grpc.ServiceCompanion[UserService] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[UserService] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = generated.user.UserProto.javaDescriptor.getServices().get(0)
  }
  
  trait UserServiceBlockingClient {
    def serviceCompanion = UserService
    def getUser(request: generated.user.UserRequest): generated.user.User
    def getUsers(request: generated.user.UsersRequest): generated.user.UserList
    def addProduct(request: generated.user.ProductUserRequest): generated.user.User
    def deleteProduct(request: generated.user.ProductUserRequest): generated.user.User
    def getProductsOfUser(request: generated.user.UserRequest): generated.user.ProductReferences
  }
  
  class UserServiceBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[UserServiceBlockingStub](channel, options) with UserServiceBlockingClient {
    override def getUser(request: generated.user.UserRequest): generated.user.User = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_GET_USER, options, request)
    }
    
    override def getUsers(request: generated.user.UsersRequest): generated.user.UserList = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_GET_USERS, options, request)
    }
    
    override def addProduct(request: generated.user.ProductUserRequest): generated.user.User = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_ADD_PRODUCT, options, request)
    }
    
    override def deleteProduct(request: generated.user.ProductUserRequest): generated.user.User = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_DELETE_PRODUCT, options, request)
    }
    
    override def getProductsOfUser(request: generated.user.UserRequest): generated.user.ProductReferences = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_GET_PRODUCTS_OF_USER, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): UserServiceBlockingStub = new UserServiceBlockingStub(channel, options)
  }
  
  class UserServiceStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[UserServiceStub](channel, options) with UserService {
    override def getUser(request: generated.user.UserRequest): scala.concurrent.Future[generated.user.User] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_GET_USER, options, request)
    }
    
    override def getUsers(request: generated.user.UsersRequest): scala.concurrent.Future[generated.user.UserList] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_GET_USERS, options, request)
    }
    
    override def addProduct(request: generated.user.ProductUserRequest): scala.concurrent.Future[generated.user.User] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_ADD_PRODUCT, options, request)
    }
    
    override def deleteProduct(request: generated.user.ProductUserRequest): scala.concurrent.Future[generated.user.User] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_DELETE_PRODUCT, options, request)
    }
    
    override def getProductsOfUser(request: generated.user.UserRequest): scala.concurrent.Future[generated.user.ProductReferences] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_GET_PRODUCTS_OF_USER, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): UserServiceStub = new UserServiceStub(channel, options)
  }
  
  def bindService(serviceImpl: UserService, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
    _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
    .addMethod(
      METHOD_GET_USER,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[generated.user.UserRequest, generated.user.User] {
        override def invoke(request: generated.user.UserRequest, observer: _root_.io.grpc.stub.StreamObserver[generated.user.User]): Unit =
          serviceImpl.getUser(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_GET_USERS,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[generated.user.UsersRequest, generated.user.UserList] {
        override def invoke(request: generated.user.UsersRequest, observer: _root_.io.grpc.stub.StreamObserver[generated.user.UserList]): Unit =
          serviceImpl.getUsers(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_ADD_PRODUCT,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[generated.user.ProductUserRequest, generated.user.User] {
        override def invoke(request: generated.user.ProductUserRequest, observer: _root_.io.grpc.stub.StreamObserver[generated.user.User]): Unit =
          serviceImpl.addProduct(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_DELETE_PRODUCT,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[generated.user.ProductUserRequest, generated.user.User] {
        override def invoke(request: generated.user.ProductUserRequest, observer: _root_.io.grpc.stub.StreamObserver[generated.user.User]): Unit =
          serviceImpl.deleteProduct(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_GET_PRODUCTS_OF_USER,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[generated.user.UserRequest, generated.user.ProductReferences] {
        override def invoke(request: generated.user.UserRequest, observer: _root_.io.grpc.stub.StreamObserver[generated.user.ProductReferences]): Unit =
          serviceImpl.getProductsOfUser(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .build()
  
  def blockingStub(channel: _root_.io.grpc.Channel): UserServiceBlockingStub = new UserServiceBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): UserServiceStub = new UserServiceStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = generated.user.UserProto.javaDescriptor.getServices().get(0)
  
}