package controllers;

import com.fasterxml.jackson.databind.JsonNode;
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

    @Inject
    public ProductsController(ProductRepository productRepository, HttpExecutionContext httpExecutionContext) {
        this.productRepository = productRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> getAllProducts(Http.Request request) {
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
}