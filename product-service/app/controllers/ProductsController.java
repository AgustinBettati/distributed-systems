package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import generated.Product;
import generated.ProductRequest;
import generated.ProductServiceClient;
import generated.ProductsRequest;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repository.ProductRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * Manage a database of computers
 */
public class ProductsController extends Controller {

    private final ProductRepository productRepository;
    private final HttpExecutionContext httpExecutionContext;

//    private final ProductServiceClient productServiceClient;

    @Inject
    public ProductsController(ProductRepository productRepository, HttpExecutionContext httpExecutionContext) {
        this.productRepository = productRepository;
        this.httpExecutionContext = httpExecutionContext;
    }
/*

    @Inject
    public ProductsController(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }
*/

    public CompletionStage<Result> getAllProducts() {
        return productRepository.getAll().thenApplyAsync(list -> {
            JsonNode jsonNode = Json.toJson(list);
            return ok(jsonNode);
        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> getProductById(int id) {
        return productRepository.getById(id).thenApplyAsync(optionalProduct -> {
            if(optionalProduct.isPresent()){
                return ok(Json.toJson(optionalProduct));
            }
            else{
                return notFound();
            }

        }, httpExecutionContext.current());
    }

    /*public CompletionStage<Product> getProductById(int id) {
        ProductRequest productRequest = ProductRequest.newBuilder().setId(id).build();
        return productServiceClient.getProduct(productRequest);
    }*/
}
