package br.com.pay.wallet.model;

import java.util.Date;
import java.util.Objects;

public class AuditLog {
    private String document;
    private String action;
    private String currency;
    private Object details;
    private Date createdAt;

    public static AuditLog build() {
        return new AuditLog();
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

    public String getCurrency() {
        return currency;
    }

    public AuditLog setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Object getDetails() {
        return details;
    }

    public AuditLog setDetails(Object details) {
        this.details = details;
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
        return Objects.equals(document, auditLog.document) && Objects.equals(action, auditLog.action) && Objects.equals(currency, auditLog.currency) && Objects.equals(details, auditLog.details) && Objects.equals(createdAt, auditLog.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, action, currency, details, createdAt);
    }
}
