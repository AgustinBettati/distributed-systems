// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/abettati/projects/tp-sistemas-distribuidos/product-service/conf/routes
// @DATE:Mon Apr 08 22:42:37 ART 2019

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:7
package controllers {

  // @LINE:7
  class ReverseProductsController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def getAllProducts(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/product")
    }
  
    // @LINE:8
    def getProductById(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/product/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
  }

  // @LINE:15
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:15
    def at(file:String): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[String]].unbind("file", file))
    }
  
  }


}
