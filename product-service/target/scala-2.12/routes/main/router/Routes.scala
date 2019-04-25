// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/abettati/projects/tp-sistemas-distribuidos/product-service/conf/routes
// @DATE:Mon Apr 08 22:42:37 ART 2019

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:7
  ProductsController_1: controllers.ProductsController,
  // @LINE:11
  routers_HelloWorldRouter_0: routers.HelloWorldRouter,
  // @LINE:15
  Assets_0: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    ProductsController_1: controllers.ProductsController,
    // @LINE:11
    routers_HelloWorldRouter_0: routers.HelloWorldRouter,
    // @LINE:15
    Assets_0: controllers.Assets
  ) = this(errorHandler, ProductsController_1, routers_HelloWorldRouter_0, Assets_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, ProductsController_1, routers_HelloWorldRouter_0, Assets_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/product""", """controllers.ProductsController.getAllProducts(request:Request)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/product/""" + "$" + """id<[^/]+>""", """controllers.ProductsController.getProductById(id:Integer)"""),
    prefixed_routers_HelloWorldRouter_0_2.router.documentation,
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.at(path:String = "/public", file:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:7
  private[this] lazy val controllers_ProductsController_getAllProducts0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/product")))
  )
  private[this] lazy val controllers_ProductsController_getAllProducts0_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      ProductsController_1.getAllProducts(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ProductsController",
      "getAllProducts",
      Seq(classOf[play.mvc.Http.Request]),
      "GET",
      this.prefix + """api/product""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_ProductsController_getProductById1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/product/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ProductsController_getProductById1_invoker = createInvoker(
    ProductsController_1.getProductById(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ProductsController",
      "getProductById",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """api/product/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private[this] val prefixed_routers_HelloWorldRouter_0_2 = Include(routers_HelloWorldRouter_0.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + ""))

  // @LINE:15
  private[this] lazy val controllers_Assets_at3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_at3_invoker = createInvoker(
    Assets_0.at(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "at",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:7
    case controllers_ProductsController_getAllProducts0_route(params@_) =>
      call { 
        controllers_ProductsController_getAllProducts0_invoker.call(
          req => ProductsController_1.getAllProducts(req))
      }
  
    // @LINE:8
    case controllers_ProductsController_getProductById1_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        controllers_ProductsController_getProductById1_invoker.call(ProductsController_1.getProductById(id))
      }
  
    // @LINE:11
    case prefixed_routers_HelloWorldRouter_0_2(handler) => handler
  
    // @LINE:15
    case controllers_Assets_at3_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at3_invoker.call(Assets_0.at(path, file))
      }
  }
}
