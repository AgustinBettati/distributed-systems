// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/abettati/projects/tp-sistemas-distribuidos/product-service/conf/routes
// @DATE:Mon Apr 08 22:42:37 ART 2019

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseProductsController ProductsController = new controllers.ReverseProductsController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseProductsController ProductsController = new controllers.javascript.ReverseProductsController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
  }

}
