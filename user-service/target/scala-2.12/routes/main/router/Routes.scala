// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/abettati/projects/tp-sistemas-distribuidos/user-service/conf/routes
// @DATE:Sun Apr 07 22:25:44 ART 2019

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:7
  UserController_0: controllers.UserController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    UserController_0: controllers.UserController
  ) = this(errorHandler, UserController_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, UserController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/user""", """controllers.UserController.getAllUsers"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/user/""" + "$" + """id<[^/]+>""", """controllers.UserController.getUserById(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/user/""" + "$" + """id<[^/]+>/references""", """controllers.UserController.getUserProducts(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/user/""" + "$" + """userId<[^/]+>/references/""" + "$" + """productId<[^/]+>""", """controllers.UserController.addProductToUser(productId:Integer, userId:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/user/""" + "$" + """userId<[^/]+>/references/delete/""" + "$" + """productId<[^/]+>""", """controllers.UserController.deleteProductOfUser(productId:Integer, userId:Integer)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:7
  private[this] lazy val controllers_UserController_getAllUsers0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/user")))
  )
  private[this] lazy val controllers_UserController_getAllUsers0_invoker = createInvoker(
    UserController_0.getAllUsers,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "getAllUsers",
      Nil,
      "GET",
      this.prefix + """api/user""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_UserController_getUserById1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/user/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_UserController_getUserById1_invoker = createInvoker(
    UserController_0.getUserById(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "getUserById",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """api/user/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_UserController_getUserProducts2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/user/"), DynamicPart("id", """[^/]+""",true), StaticPart("/references")))
  )
  private[this] lazy val controllers_UserController_getUserProducts2_invoker = createInvoker(
    UserController_0.getUserProducts(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "getUserProducts",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """api/user/""" + "$" + """id<[^/]+>/references""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_UserController_addProductToUser3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/user/"), DynamicPart("userId", """[^/]+""",true), StaticPart("/references/"), DynamicPart("productId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_UserController_addProductToUser3_invoker = createInvoker(
    UserController_0.addProductToUser(fakeValue[Integer], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "addProductToUser",
      Seq(classOf[Integer], classOf[Integer]),
      "GET",
      this.prefix + """api/user/""" + "$" + """userId<[^/]+>/references/""" + "$" + """productId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_UserController_deleteProductOfUser4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/user/"), DynamicPart("userId", """[^/]+""",true), StaticPart("/references/delete/"), DynamicPart("productId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_UserController_deleteProductOfUser4_invoker = createInvoker(
    UserController_0.deleteProductOfUser(fakeValue[Integer], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "deleteProductOfUser",
      Seq(classOf[Integer], classOf[Integer]),
      "GET",
      this.prefix + """api/user/""" + "$" + """userId<[^/]+>/references/delete/""" + "$" + """productId<[^/]+>""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:7
    case controllers_UserController_getAllUsers0_route(params@_) =>
      call { 
        controllers_UserController_getAllUsers0_invoker.call(UserController_0.getAllUsers)
      }
  
    // @LINE:8
    case controllers_UserController_getUserById1_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        controllers_UserController_getUserById1_invoker.call(UserController_0.getUserById(id))
      }
  
    // @LINE:10
    case controllers_UserController_getUserProducts2_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_UserController_getUserProducts2_invoker.call(UserController_0.getUserProducts(id))
      }
  
    // @LINE:11
    case controllers_UserController_addProductToUser3_route(params@_) =>
      call(params.fromPath[Integer]("productId", None), params.fromPath[Integer]("userId", None)) { (productId, userId) =>
        controllers_UserController_addProductToUser3_invoker.call(UserController_0.addProductToUser(productId, userId))
      }
  
    // @LINE:12
    case controllers_UserController_deleteProductOfUser4_route(params@_) =>
      call(params.fromPath[Integer]("productId", None), params.fromPath[Integer]("userId", None)) { (productId, userId) =>
        controllers_UserController_deleteProductOfUser4_invoker.call(UserController_0.deleteProductOfUser(productId, userId))
      }
  }
}
