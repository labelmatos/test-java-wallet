package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.DepositDTO;
import br.com.pay.wallet.model.Statement;
import br.com.pay.wallet.model.Wallet;
import br.com.pay.wallet.repository.StatementRepository;
import br.com.pay.wallet.repository.WalletsRepository;
import br.com.pay.wallet.util.DateUtil;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Service
public class DepositService {

    @Autowired
    private AuditService auditService;
    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private WalletsRepository walletsRepository;
    @Autowired
    private StatementRepository statementRepository;

    public void deposit(String walletId, DepositDTO dto, String document) throws Exception {
        final Wallet wallet = walletsRepository.findById(walletId);

        if (wallet == null || !wallet.getOwnerDocument().equals(document)) {
            throw new IllegalArgumentException("Wallet not found.");
        }

        if (!wallet.getCurrency().equals(dto.currency)) {
            throw new IllegalArgumentException("Wrong currency for this wallet.");
        }

        final Statement last = statementRepository.findById(walletId);
        final double previousValue = last != null ? last.getFinalValue() : 0.0;
        final double finalValue = previousValue + dto.value;

        statementRepository.create(Statement.build()
                .setWalletId(walletId)
                .setValue(dto.value)
                .setCurrency(dto.currency)
                .setPreviousValue(previousValue)
                .setOperation("deposit")
                .setFinalValue(finalValue)
                .setSearchDate(DateUtil.toNumeric(LocalDate.now()))
                .setCreatedAt(Date.from(Instant.now())));
        auditService.log(document, "DEPOSIT", new Document("walletId", walletId)
                .append("operation", "deposit")
                .append("value", dto.value));
    }
}
