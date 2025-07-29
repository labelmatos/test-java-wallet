package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.TransferDTO;
import br.com.pay.wallet.model.CurrencyTax;
import br.com.pay.wallet.model.Statement;
import br.com.pay.wallet.model.TransferMetadata;
import br.com.pay.wallet.model.Wallet;
import br.com.pay.wallet.repository.CurrencyTaxRepository;
import br.com.pay.wallet.repository.StatementRepository;
import br.com.pay.wallet.repository.WalletsRepository;
import br.com.pay.wallet.util.DateUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;

@Service
public class TransferService {

    @Autowired
    private AuditService auditService;
    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private WalletsRepository walletsRepository;
    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private CurrencyTaxRepository currencyTaxRepository;

    public void transfer(String walletId, TransferDTO dto, String document) throws Exception {

        final Wallet originWallet = walletsRepository.findById(walletId);
        if (originWallet == null || !originWallet.getOwnerDocument().equals(document)) {
            throw new IllegalArgumentException("Origin wallet not found.");
        }

        final Wallet targetWallet = walletsRepository.findMineById(dto.targetWalletId, dto.targetDocument);
        if (targetWallet == null) {
            throw new IllegalArgumentException("target wallet not found.");
        }

        String originCurrency = originWallet.getCurrency();
        String targetCurrency = targetWallet.getCurrency();

        final Statement lastOrigin = statementRepository.findById(walletId);
        if (dto.value > lastOrigin.getFinalValue()) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        double defaultRate = 1.0;
        if (!originCurrency.equals(targetCurrency)) {
            final CurrencyTax currencyTax = currencyTaxRepository.find(originCurrency + "_" + targetCurrency);
            if (currencyTax != null) {
                defaultRate = currencyTax.getRate();
            }
        }

        statementRepository.create(Statement.build()
                .setWalletId(walletId)
                .setValue(dto.value)
                .setCurrency(originCurrency)
                .setPreviousValue(lastOrigin.getFinalValue())
                .setOperation("transfer")
                .setFinalValue(lastOrigin.getFinalValue() - dto.value)
                .setTo(TransferMetadata.build()
                        .setCurrency(dto.targetWalletId)
                        .setDocument(dto.targetDocument)
                        .setWallet(targetCurrency))
                .setSearchDate(DateUtil.toNumeric(LocalDate.now()))
                .setCreatedAt(Date.from(Instant.now())));

        double convertedValue = dto.value * defaultRate;
        final Statement targetLastStatement = statementRepository.findById(dto.targetWalletId);
        statementRepository.create(Statement.build()
                .setWalletId(dto.targetWalletId)
                .setValue(convertedValue)
                .setCurrency(targetCurrency)
                .setPreviousValue(targetLastStatement.getFinalValue())
                .setOperation("transfer")
                .setFinalValue(targetLastStatement.getFinalValue() + convertedValue)
                .setFrom(TransferMetadata.build()
                        .setCurrency(walletId)
                        .setDocument(document)
                        .setWallet(originCurrency))
                .setSearchDate(DateUtil.toNumeric(LocalDate.now()))
                .setCreatedAt(Date.from(Instant.now())));
        auditService.log(document, "TRANSFER", new Document("from", walletId).append("to", dto.targetWalletId));
    }
}
