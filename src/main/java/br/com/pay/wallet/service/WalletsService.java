package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.WalletDTO;
import br.com.pay.wallet.model.Statement;
import br.com.pay.wallet.model.Wallet;
import br.com.pay.wallet.repository.StatementRepository;
import br.com.pay.wallet.repository.WalletsRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletsService {

    @Autowired
    private AuditService auditService;
    private WalletsRepository walletsRepository;
    private StatementRepository statementRepository;

    public void createWallet(WalletDTO dto, String document) {
        walletsRepository.create(Wallet.build()
                .setWalletName(dto.walletName)
                .setCurrency(dto.currency)
                .setOwnerDocument(document)
                .setCreatedAt(Date.from(Instant.now())));
    }

    public List<WalletDTO> listWallets(String document) {
        return walletsRepository.list(document)
                .stream()
                .map(Wallet::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteWallet(String walletId, String clientDocument) {
        final Wallet wallet = walletsRepository.findById(walletId);
        if (wallet == null || !wallet.getOwnerDocument().equals(clientDocument)) {
            throw new IllegalArgumentException("Wallet not found.");
        }

        final Statement lastStatement = statementRepository.findById(walletId);
        if (lastStatement != null && lastStatement.getFinalValue() > 0) {
            throw new IllegalArgumentException("Wallet cannot be deleted with positive balance.");
        }

        walletsRepository.delete(walletId);
        auditService.log(clientDocument, "DELETE_WALLET", new Document("walletId", walletId));
    }
}