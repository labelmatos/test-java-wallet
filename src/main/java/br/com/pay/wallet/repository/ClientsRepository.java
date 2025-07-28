package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.Client;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class ClientsRepository {

    @Autowired
    private MongoClient mongoClient;

    public void create(Client client) {
        getMongoCollection().insertOne(client);
    }

    public Client find(String document) {
        return getMongoCollection().find(eq("document", document)).first();
    }

    public Client logIn(String document, String password) {
        return getMongoCollection().find(new Document("document", document).append("password", password)).first();
    }

    public void update(String document, Document updateFields) {
        getMongoCollection().updateOne(eq("document", document), new Document("$set", updateFields));
    }

    public void delete(String document) {
        getMongoCollection().updateOne(eq("document", new org.bson.types.ObjectId(document)), new Document("$set", new Document("deleted", true)));
    }

    private MongoCollection<Client> getMongoCollection() {
        return mongoClient
                .getDatabase("wallet")
                .getCollection("clients", Client.class);
    }
}
