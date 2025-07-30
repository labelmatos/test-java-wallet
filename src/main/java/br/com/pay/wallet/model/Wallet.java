package br.com.pay.wallet.model;

import br.com.pay.wallet.dto.WalletDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "wallets")
public class Wallet {
    @Id
    private String id;
    private String walletName;
    private String currency;
    private String ownerDocument;
    private Date createdAt;

    public static Wallet newInstance() {
        return new Wallet();
    }

    public String getId() {
        return id;
    }

    public Wallet setId(String id) {
        this.id = id;
        return this;
    }

    public String getWalletName() {
        return walletName;
    }

    public Wallet setWalletName(String walletName) {
        this.walletName = walletName;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Wallet setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getOwnerDocument() {
        return ownerDocument;
    }

    public Wallet setOwnerDocument(String ownerDocument) {
        this.ownerDocument = ownerDocument;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Wallet setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) && Objects.equals(walletName, wallet.walletName) && Objects.equals(currency, wallet.currency) && Objects.equals(ownerDocument, wallet.ownerDocument) && Objects.equals(createdAt, wallet.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, walletName, currency, ownerDocument, createdAt);
    }

    public WalletDTO toDTO() {
        return WalletDTO.build()
                .setId(this.id)
                .setCurrency(this.currency)
                .setWalletName(this.walletName);
    }
}
