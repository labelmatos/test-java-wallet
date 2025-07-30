package br.com.pay.wallet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "auditLogs")
public class AuditLog {
    @Id
    private String id;
    private String document;
    private String action;
    private Object details;
    private int searchDate;
    private Date createdAt;

    public static AuditLog build() {
        return new AuditLog();
    }

    public String getId() {
        return id;
    }

    public AuditLog setId(String id) {
        this.id = id;
        return this;
    }

    public String getDocument() {
        return document;
    }

    public AuditLog setDocument(String document) {
        this.document = document;
        return this;
    }

    public String getAction() {
        return action;
    }

    public AuditLog setAction(String action) {
        this.action = action;
        return this;
    }

    public Object getDetails() {
        return details;
    }

    public AuditLog setDetails(Object details) {
        this.details = details;
        return this;
    }

    public int getSearchDate() {
        return searchDate;
    }

    public AuditLog setSearchDate(int searchDate) {
        this.searchDate = searchDate;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public AuditLog setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return searchDate == auditLog.searchDate && Objects.equals(id, auditLog.id) && Objects.equals(document, auditLog.document) && Objects.equals(action, auditLog.action) && Objects.equals(details, auditLog.details) && Objects.equals(createdAt, auditLog.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, document, action, details, searchDate, createdAt);
    }
}
