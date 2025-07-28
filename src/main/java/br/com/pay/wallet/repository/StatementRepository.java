package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.Statement;
import br.com.pay.wallet.util.DateUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

@Repository
public class StatementRepository {

    @Autowired
    private MongoClient mongoClient;

    public Statement findById(String walletId) {
        return getMongoCollection().find(eq("walletId", walletId)).sort(descending("createdAt")).first();
    }

    public void create(Statement statement) {
        getMongoCollection().insertOne(statement);
    }

    public List<Statement> list(String walletId, int limit) {
        if(limit > 0) {
            return getMongoCollection().find(eq("walletId", walletId)).sort(ascending("createdAt")).limit(limit).into(new ArrayList<>());
        }
        return getMongoCollection().find(eq("walletId", walletId)).sort(ascending("createdAt")).into(new ArrayList<>());
    }

    public List<Statement> list(String walletId, LocalDate startDate, LocalDate endDate) throws Exception {
        Bson filter = and(
                eq("walletId", walletId),
                gte("searchDate", DateUtil.toNumeric(startDate))
        );

        if (endDate != null) {
            filter = and(filter, lte("searchDate", DateUtil.toNumeric(endDate)));
        }

//        if(limit > 0) {
//            return getMongoCollection().find(eq("walletId", walletId)).sort(ascending("createdAt")).limit(limit).into(new ArrayList<>());
//        }
        return getMongoCollection().find(filter).sort(ascending("createdAt")).into(new ArrayList<>());
//        return getMongoCollection().find(eq("walletId", walletId)).sort(ascending("createdAt")).into(new ArrayList<>());
    }

    private MongoCollection<Statement> getMongoCollection() {
        return mongoClient
                .getDatabase("wallet")
                .getCollection("bankStatement", Statement.class);
    }
}
