package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.BalanceDTO;
import br.com.pay.wallet.dto.StatementDTO;
import br.com.pay.wallet.model.Statement;
import br.com.pay.wallet.model.Wallet;
import br.com.pay.wallet.repository.StatementRepository;
import br.com.pay.wallet.repository.WalletsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BalanceService {

    @Autowired
    private AuditService auditService; // TODO: implementar
    @Autowired
    private WalletsRepository walletsRepository;
    @Autowired
    private StatementRepository statementRepository;

    public BalanceDTO getWalletBalance(String walletId, String document) {
        final Wallet wallet = walletsRepository.findByIdAndOwnerDocument(walletId, document);
        if (wallet == null || !wallet.getOwnerDocument().equals(document)) {
            throw new IllegalArgumentException("Wallet not found.");
        }

        final Statement lastStatement = statementRepository.findFirstByWalletIdOrderByCreatedAtDesc(walletId);
        if (lastStatement != null) {
            return lastStatement.toBalanceDTO(wallet);
        }
        return BalanceDTO.build()
                .setWalletId(walletId)
                .setWalletName(wallet.getWalletName())
                .setBalance(0.0)
                .setCurrency(wallet.getCurrency());
    }

    public List<BalanceDTO> getAllWalletBalances(String document) {
        List<Wallet> wallets = walletsRepository.findByOwnerDocument(document);
        return wallets.stream()
                .map(wallet -> this.getWalletBalance(wallet.getId(), document))
                .collect(Collectors.toList());
    }

    public List<StatementDTO> getLastOperations(String walletId, String document) {
        final Wallet wallet = walletsRepository.findByIdAndOwnerDocument(walletId, document);
        if (wallet == null || !wallet.getOwnerDocument().equals(document)) {
            throw new IllegalArgumentException("Wallet not found.");
        }
        return statementRepository.findByWalletIdOrderByCreatedAtDesc(walletId)
                .stream()
                .map(Statement::toDTO)
                .collect(Collectors.toList());
    }

    public List<StatementDTO> getStatementByDate(String walletId, String document, LocalDate startDate, LocalDate endDate) throws Exception {
        final Wallet wallet = walletsRepository.findByIdAndOwnerDocument(walletId, document);
        if (wallet == null || !wallet.getOwnerDocument().equals(document)) {
            throw new IllegalArgumentException("Wallet not found.");
        }
        return statementRepository.findByWalletIdOrderByCreatedAtDesc(walletId)//.list(walletId, startDate, endDate)
                .stream()
                .map(Statement::toDTO)
                .collect(Collectors.toList());
    }
}
