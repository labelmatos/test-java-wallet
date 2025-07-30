package br.com.pay.wallet.service;

import br.com.pay.wallet.model.AuditLog;
import br.com.pay.wallet.repository.AuditRepository;
import br.com.pay.wallet.util.DateUtil;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class AuditService {

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private AuditRepository auditRepository;

    public void log(String document, String action, Object details) throws Exception {
        auditRepository.insert(AuditLog.build()
                .setDocument(document)
                .setAction(action)
                .setDetails(details)
                .setSearchDate(DateUtil.toNumeric(LocalDate.now()))
                .setCreatedAt(Date.from(Instant.now())));
    }

    public List<AuditLog> getLogsByDocument(String document, LocalDate startDate, LocalDate endDate) throws Exception {
        if(startDate != null && endDate != null) {
            return auditRepository.findByDocumentAndSearchDateBetweenOrderByCreatedAtDesc(document, DateUtil.toNumeric(startDate), DateUtil.toNumeric(endDate));
        }
        return auditRepository.findByDocumentOrderByCreatedAtDesc(document);
    }
}



