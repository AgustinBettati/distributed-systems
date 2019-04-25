// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/abettati/projects/tp-sistemas-distribuidos/user-service/conf/routes
// @DATE:Sun Apr 07 22:25:44 ART 2019


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
