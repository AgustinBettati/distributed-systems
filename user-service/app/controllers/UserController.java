package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.UserRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * Manage a database of computers
 */
public class UserController extends Controller {

    private final UserRepository userRepository;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public UserController(UserRepository userRepository, HttpExecutionContext httpExecutionContext) {
        this.userRepository = userRepository;
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
        return userRepository.getProductsById(id).thenApplyAsync(user -> {
            if (user.isPresent()) {
                return ok(Json.toJson(user.get().products));
            } else {
                return notFound();
            }

        }, httpExecutionContext.current());
    }

    // todo
    public CompletionStage<Result> addProductToUser(int productId, int userId) {

    }

    //todo
    public CompletionStage<Result> deleteProductFromUser(int productId, int userId) {

    }
}
