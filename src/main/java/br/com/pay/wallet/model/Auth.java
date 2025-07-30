package br.com.pay.wallet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "authLogs")
public class Auth {
    @Id
    private String id;
    private String document;
    private String status;
    private Date createdAt;

    public static Auth build() {
        return new Auth();
    }

    public String getId() {
        return id;
    }

    public Auth setId(String id) {
        this.id = id;
        return this;
    }

    public String getDocument() {
        return document;
    }

    public Auth setDocument(String document) {
        this.document = document;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Auth setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Auth setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Auth auth = (Auth) o;
        return Objects.equals(document, auth.document) && Objects.equals(status, auth.status) && Objects.equals(createdAt, auth.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, status, createdAt);
    }
}
