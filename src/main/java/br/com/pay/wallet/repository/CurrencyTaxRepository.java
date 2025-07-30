package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.CurrencyTax;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyTaxRepository extends MongoRepository<CurrencyTax, String> {

}
