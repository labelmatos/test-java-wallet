package br.com.pay.wallet.repository;

import br.com.pay.wallet.model.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends MongoRepository<AuditLog, String> {

    List<AuditLog> findByDocumentOrderByCreatedAtDesc(String document);

    List<AuditLog> findByDocumentAndSearchDateBetweenOrderByCreatedAtDesc(String document, int numeric, int numeric1);

}
