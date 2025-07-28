package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.CurrencyTax;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class CurrencyTaxRepository {

    @Autowired
    private MongoClient mongoClient;

    public CurrencyTax find(String id) {
        return getMongoCollection().find(eq("_id", new org.bson.types.ObjectId(id))).first();
    }

    private MongoCollection<CurrencyTax> getMongoCollection() {
        return mongoClient
                .getDatabase("wallet")
                .getCollection("currencyTaxes", CurrencyTax.class);
    }
}
