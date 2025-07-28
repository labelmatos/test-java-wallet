package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.AuditLog;
import br.com.pay.wallet.model.Client;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;

@Repository
public class AuditRepository {

    @Autowired
    private MongoClient mongoClient;

    public void create(AuditLog auditLog) {
        getMongoCollection().insertOne(auditLog);
    }

    private MongoCollection<AuditLog> getMongoCollection() {
        return mongoClient
                .getDatabase("wallet")
                .getCollection("auditLog", AuditLog.class);
    }

    public List<AuditLog> list(String document) {
        return getMongoCollection().find(eq("document", document)).sort(ascending("timestamp")).into(new ArrayList<>());
    }

    public List<AuditLog> findByDate(String document, String startDate, String endDate) {
        Bson filter = eq("document", document);
        List<Bson> conditions = new ArrayList<>();
        conditions.add(filter);

        if (startDate != null || endDate != null) {
            Bson dateFilter = new Document();
            if (startDate != null) {
                dateFilter = Filters.and(dateFilter, gte("createdAt", startDate));
            }
            if (endDate != null) {
                dateFilter = Filters.and(dateFilter, lte("createdAt", endDate));
            }
            conditions.add(dateFilter);
        }
        return getMongoCollection().find(and(conditions)).sort(ascending("createdAt")).into(new ArrayList<>());
    }
}
