package models;

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
    public List<Client> clients;
}
