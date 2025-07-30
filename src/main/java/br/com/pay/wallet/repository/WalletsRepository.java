package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletsRepository extends MongoRepository<Wallet, String> {
    List<Wallet> findByOwnerDocument(String document);

    Wallet findByIdAndOwnerDocument(String id, String document);

}
