package repositories;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Reference;
import models.User;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * A repository that executes database operations in a different
 * execution context.
 */
public class UserRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public UserRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<List<User>> getAll() {
        return supplyAsync(() ->
                ebeanServer.find(User.class).findList(), executionContext);
    }

    public CompletionStage<Optional<User>> getById(int id) {
        return supplyAsync(() ->
                ebeanServer.find(User.class).where().eq("id", id).findOneOrEmpty(), executionContext);
    }

    public void addExistingProductToUser(int userId, Optional<Reference> product) {
        Reference reference1 = product.get();
        CompletionStage<Optional<User>> user = getById(userId);

        user.thenApplyAsync(user1 -> {
            user1.get().references.add(reference1);
            user1.get().save();
            return user1;
        });
    }

    public void deleteExistingProductToUser(int userId, Optional<Reference> product) {
        Reference reference1 = product.get();
        CompletionStage<Optional<User>> user = getById(userId);

        user.thenApplyAsync(user1 -> {
            user1.get().references.remove(reference1);
            user1.get().save();
            return user1;
        });
    }
}
