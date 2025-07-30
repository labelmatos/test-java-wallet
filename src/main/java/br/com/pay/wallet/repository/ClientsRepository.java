package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends MongoRepository<Client, String> {
    Client findByIdAndPasswordAndDeletedFalse(String id, String password);

}
