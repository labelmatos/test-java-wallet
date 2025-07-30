package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.Auth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends MongoRepository<Auth, String> {

}
