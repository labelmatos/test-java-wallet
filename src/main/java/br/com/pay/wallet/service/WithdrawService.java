package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.WithdrawDTO;
import br.com.pay.wallet.model.Statement;
import br.com.pay.wallet.model.Wallet;
import br.com.pay.wallet.repository.StatementRepository;
import br.com.pay.wallet.repository.WalletsRepository;
import br.com.pay.wallet.util.DateUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Service
public class WithdrawService {

    @Autowired
    private AuditService auditService;
    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private WalletsRepository walletsRepository;

    public void withdraw(String walletId, WithdrawDTO dto, String document) throws Exception {
        final Wallet wallet = walletsRepository.findByIdAndOwnerDocument(walletId, document);
        if (wallet == null || !wallet.getOwnerDocument().equals(document)) {
            throw new IllegalArgumentException("Wallet not found.");
        }

        if (!wallet.getCurrency().equals(dto.currency)) {
            throw new IllegalArgumentException("Informed currency is different than wallet's currency.");
        }

        final Statement last = statementRepository.findFirstByWalletIdOrderByCreatedAtDesc(walletId);
        final double previous = last != null ? last.getFinalValue() : 0.0;

        if (dto.value > previous) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        final double finalValue = previous - dto.value;

        statementRepository.insert(Statement.newInstance()
                .setWalletId(walletId)
                .setValue(dto.value)
                .setCurrency(dto.currency)
                .setPreviousValue(previous)
                .setOperation("withdraw")
                .setFinalValue(finalValue)
                .setSearchDate(DateUtil.toNumeric(LocalDate.now()))
                .setCreatedAt(Date.from(Instant.now())));
        auditService.log(document, "WITHDRAW", new Document("walletId", walletId)
                .append("operation", "withdraw")
                .append("value", dto.value));
    }
}
