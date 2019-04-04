package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.ProductRepository;
import repositories.UserRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * Manage a database of computers
 */
public class UserController extends Controller {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public UserController(UserRepository userRepository, ProductRepository productRepository, HttpExecutionContext httpExecutionContext) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> getAllUsers() {
        return userRepository.getAll().thenApplyAsync(list -> {
            JsonNode jsonNode = Json.toJson(list);
            return ok(jsonNode);
        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> getUserById(int id) {
        return userRepository.getById(id).thenApplyAsync(optionalClient -> {
            if (optionalClient.isPresent()) {
                return ok(Json.toJson(optionalClient));
            } else {
                return notFound();
            }

        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> getUserProducts(Long id) {
        return userRepository.getById(Math.toIntExact(id)).thenApplyAsync(user -> {
            if (user.isPresent()) {
                return ok(Json.toJson(user.get().products));
            } else {
                return notFound();
            }

        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> addProductToUser(int productId, int userId) {
        return productRepository.getById(productId).thenApplyAsync(product -> {
            if (product.isPresent()) {
                userRepository.addExistingProductToUser(userId, product);
                return ok(Json.toJson(product.get()));

            } else {
                return notFound();

            }

        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> deleteProductOfUser(int productId, int userId) {
        return productRepository.getById(productId).thenApplyAsync(product -> {
            if (product.isPresent()) {
                userRepository.deleteExistingProductToUser(userId, product);
                return ok(Json.toJson(product.get()));

            } else {
                return notFound();

            }

        }, httpExecutionContext.current());
    }
}
