package repositories;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Finder;
import models.Client;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * A repository that executes database operations in a different
 * execution context.
 */
public class ClientRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public ClientRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<List<Client>> getAll() {
        return supplyAsync(() ->
                ebeanServer.find(Client.class).findList(), executionContext);
    }

    public CompletionStage<Optional<Client>> getById(int id) {
        return supplyAsync(() ->
                ebeanServer.find(Client.class).where().eq("id", id).findOneOrEmpty(), executionContext);
    }

    public CompletionStage<List<UUID>> getProductsById(UUID id) {
        return supplyAsync(() ->
                ebeanServer.find(Client.class)
                        .where().eq("id", id)
                        .select("products")
                        .findSingleAttributeList()
                , executionContext);
    }
}
