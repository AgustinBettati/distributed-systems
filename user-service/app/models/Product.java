package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Product extends Model {

    @Id
    public UUID id;

    @ManyToMany
    @JsonIgnoreProperties("products")
    public List<Client> clients;
}
