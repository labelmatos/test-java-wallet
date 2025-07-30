package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.WalletDTO;
import br.com.pay.wallet.model.Statement;
import br.com.pay.wallet.model.Wallet;
import br.com.pay.wallet.repository.StatementRepository;
import br.com.pay.wallet.repository.WalletsRepository;
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
    @Autowired
    private WalletsRepository walletsRepository;
    @Autowired
    private StatementRepository statementRepository;

    public void createWallet(WalletDTO dto, String document) {
        walletsRepository.insert(Wallet.newInstance()
                .setWalletName(dto.walletName)
                .setCurrency(dto.currency)
                .setOwnerDocument(document)
                .setCreatedAt(Date.from(Instant.now())));
    }

    public List<WalletDTO> listWallets(String document) {
        return walletsRepository.findByOwnerDocument(document)
                .stream()
                .map(Wallet::toDTO)
                .collect(Collectors.toList());
    }

    public WalletDTO findWallet(String walletId, String document) {
        final Wallet wallet = walletsRepository.findByIdAndOwnerDocument(walletId, document);
        if (wallet == null || !wallet.getOwnerDocument().equals(document)) {
            throw new IllegalArgumentException("Wallet not found.");
        }
        return wallet.toDTO();
    }

    public void deleteWallet(String walletId, String document) {
        final Wallet wallet = walletsRepository.findByIdAndOwnerDocument(walletId, document);
        if (wallet == null || !wallet.getOwnerDocument().equals(document)) {
            throw new IllegalArgumentException("Wallet not found.");
        }

        final Statement lastStatement = statementRepository.findFirstByWalletIdOrderByCreatedAtDesc(walletId);
        if (lastStatement != null && lastStatement.getFinalValue() > 0) {
            throw new IllegalArgumentException("Wallet cannot be deleted with positive balance.");
        }

        walletsRepository.delete(wallet);
        auditService.log(document, "DELETE_WALLET", wallet);
    }
}