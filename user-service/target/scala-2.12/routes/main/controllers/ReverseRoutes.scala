// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/abettati/projects/tp-sistemas-distribuidos/user-service/conf/routes
// @DATE:Sun Apr 07 22:25:44 ART 2019

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:7
package controllers {

  // @LINE:7
  class ReverseUserController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def getUserById(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/user/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:11
    def addProductToUser(productId:Integer, userId:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/user/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("userId", userId)) + "/references/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("productId", productId)))
    }
  
    // @LINE:12
    def deleteProductOfUser(productId:Integer, userId:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/user/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("userId", userId)) + "/references/delete/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("productId", productId)))
    }
  
    // @LINE:7
    def getAllUsers(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/user")
    }
  
    // @LINE:10
    def getUserProducts(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/user/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)) + "/references")
    }
  
  }


}
