package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.WithdrawDTO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

@Service
public class WithdrawService {

    @Autowired
    private AuditService auditService;
    private MongoClient mongoClient;

    public void withdraw(String walletId, WithdrawDTO dto, String document) {
        MongoDatabase db = mongoClient.getDatabase("wallet");
        MongoCollection<Document> wallets = db.getCollection("wallets");
        MongoCollection<Document> statements = db.getCollection("bankStatement");

        Document wallet = wallets.find(eq("_id", new org.bson.types.ObjectId(walletId))).first();

        if (wallet == null || !wallet.getString("ownerDocument").equals(document)) {
            throw new IllegalArgumentException("Wallet não encontrada ou não pertence ao usuário.");
        }

        String walletCurrency = wallet.getString("currency");
        if (!walletCurrency.equals(dto.currency)) {
            throw new IllegalArgumentException("Moeda informada diferente da moeda da wallet.");
        }

        Document last = statements.find(eq("walletId", walletId)).sort(descending("createdAt")).first();
        double previous = last != null ? last.getDouble("finalValue") : 0.0;

        if (dto.value > previous) {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }

        double finalValue = previous - dto.value;

        Document record = new Document()
                .append("walletId", walletId)
                .append("value", dto.value)
                .append("currency", dto.currency)
                .append("previousValue", previous)
                .append("operation", "withdraw")
                .append("finalValue", finalValue)
                .append("searchTerms", new Document("date", LocalDate.now().toString()))
                .append("createdAt", Instant.now().toString());

        statements.insertOne(record);
        auditService.log(document, "WITHDRAW", record);
    }
}
