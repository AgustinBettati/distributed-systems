package repositories;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Product;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class ProductRepository {
    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public ProductRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<Optional<Product>> getById(int id) {
        return supplyAsync(() ->
                ebeanServer.find(Product.class).where().eq("id", id).findOneOrEmpty(), executionContext);
    }


}