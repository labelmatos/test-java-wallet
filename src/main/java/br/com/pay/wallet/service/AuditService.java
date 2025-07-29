package br.com.pay.wallet.service;

import br.com.pay.wallet.model.AuditLog;
import br.com.pay.wallet.repository.AuditRepository;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class AuditService {

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private AuditRepository auditRepository;

    public void log(String document, String action, Object details) {
        auditRepository.create(AuditLog.build()
                .setDocument(document)
                .setAction(action)
                .setDetails(details)
                .setCreatedAt(Date.from(Instant.now())));
    }

    public List<AuditLog> getLogsByDocument(String document) {
        return auditRepository.list(document);
    }

    public List<AuditLog> getLogsByDocument(String document, String startDate, String endDate) {
        return auditRepository.findByDate(document, startDate, endDate);
    }
}



