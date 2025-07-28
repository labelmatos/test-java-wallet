package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.Wallet;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

@Repository
public class WalletsRepository {

    @Autowired
    private MongoClient mongoClient;

    public void create(Wallet wallet) {
        getMongoCollection().insertOne(wallet);
    }

    public List<Wallet> list(String document) {
        return getMongoCollection().find((and(eq("ownerDocument", document), ne("deleted", true))))
                .into(new ArrayList<>());
    }

    public Wallet findById(String walletId) {
        return getMongoCollection().find(eq("_id", new org.bson.types.ObjectId(walletId))).first();
    }

    public Wallet findMineById(String walletId, String ownerDocument) {
        return getMongoCollection().find(and(eq("_id", new org.bson.types.ObjectId(walletId)),eq("ownerDocument", ownerDocument))).first();
    }

    public void delete(String walletId) {
        getMongoCollection().updateOne(eq("_id", new org.bson.types.ObjectId(walletId)), new Document("$set", new Document("deleted", true)));
    }

    private MongoCollection<Wallet> getMongoCollection() {
        return mongoClient
                .getDatabase("wallet")
                .getCollection("wallets", Wallet.class);
    }
}
