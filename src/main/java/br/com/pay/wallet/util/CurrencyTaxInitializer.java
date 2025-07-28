package br.com.pay.wallet.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CurrencyTaxInitializer implements CommandLineRunner {

    @Autowired
    private MongoClient mongoClient;

    @Override
    public void run(String... args) {
        MongoDatabase db = mongoClient.getDatabase("wallet");
        MongoCollection<Document> collection = db.getCollection("currencyTaxes");

        if (collection.countDocuments() == 0) {
            List<Document> taxes = Arrays.asList(
                    new Document("_id", "USD_BRL").append("rate", 5.00),
                    new Document("_id", "BRL_USD").append("rate", 0.20),
                    new Document("_id", "USD_EUR").append("rate", 0.90),
                    new Document("_id", "EUR_USD").append("rate", 1.10),
                    new Document("_id", "EUR_BRL").append("rate", 5.50),
                    new Document("_id", "BRL_EUR").append("rate", 0.18),
                    new Document("_id", "USD_GBP").append("rate", 0.75),
                    new Document("_id", "GBP_USD").append("rate", 1.33)
            );
            collection.insertMany(taxes);
        }
    }
}
