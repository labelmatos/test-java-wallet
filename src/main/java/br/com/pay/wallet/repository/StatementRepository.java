package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.Statement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends MongoRepository<Statement, String> {
    List<Statement> findByWalletIdOrderByCreatedAtDesc(String walletId);

    List<Statement> findByWalletIdAndSearchDateBetweenOrderByCreatedAtDesc(String walletId, int searchDateStart, int searchDateEnd);

    Statement findFirstByWalletIdOrderByCreatedAtDesc(String walletId);


}
