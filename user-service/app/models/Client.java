package models;

import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Client extends Model {
    private static final long serialVersionUID = 1L;

    @Id
    public UUID id;

    @Constraints.Required
    public String name;

    @ManyToMany (mappedBy = "clients")
    @Constraints.Required
    public List<Product> products;

}