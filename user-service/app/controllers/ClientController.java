package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.ClientRepository;

import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

/**
 * Manage a database of computers
 */
public class ClientController extends Controller {

    private final ClientRepository clientRepository;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public ClientController(ClientRepository clientRepository, HttpExecutionContext httpExecutionContext) {
        this.clientRepository = clientRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> getAllClients() {
        return clientRepository.getAll().thenApplyAsync(list -> {
            JsonNode jsonNode = Json.toJson(list);
            return ok(jsonNode);
        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> getClientById(int id) {
        return clientRepository.getById(id).thenApplyAsync(optionalClient -> {
            if (optionalClient.isPresent()) {
                return ok(Json.toJson(optionalClient));
            } else {
                return notFound();
            }

        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> getClientProducts(UUID id) {
        return clientRepository.getProductsById(id).thenApplyAsync(products -> {
            if (products != null) {
                return ok(Json.toJson(products));
            } else {
                return notFound();
            }

        }, httpExecutionContext.current());
    }
}