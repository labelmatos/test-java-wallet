package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.Auth;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {

    @Autowired
    private MongoClient mongoClient;

    public void create(Auth auth) {
        getMongoCollection().insertOne(auth);
    }

    private MongoCollection<Auth> getMongoCollection() {
        return mongoClient
                .getDatabase("wallet")
                .getCollection("authRegister", Auth.class);
    }
}
