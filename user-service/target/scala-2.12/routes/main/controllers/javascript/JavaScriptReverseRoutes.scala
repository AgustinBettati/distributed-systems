// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/abettati/projects/tp-sistemas-distribuidos/user-service/conf/routes
// @DATE:Sun Apr 07 22:25:44 ART 2019

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:7
package controllers.javascript {

  // @LINE:7
  class ReverseUserController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def getUserById: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.getUserById",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:11
    def addProductToUser: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.addProductToUser",
      """
        function(productId0,userId1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("userId", userId1)) + "/references/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("productId", productId0))})
        }
      """
    )
  
    // @LINE:12
    def deleteProductOfUser: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.deleteProductOfUser",
      """
        function(productId0,userId1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("userId", userId1)) + "/references/delete/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("productId", productId0))})
        }
      """
    )
  
    // @LINE:7
    def getAllUsers: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.getAllUsers",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user"})
        }
      """
    )
  
    // @LINE:10
    def getUserProducts: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UserController.getUserProducts",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/user/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("id", id0)) + "/references"})
        }
      """
    )
  
  }


}
