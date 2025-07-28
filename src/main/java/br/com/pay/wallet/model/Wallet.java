package br.com.pay.wallet.model;

import br.com.pay.wallet.dto.WalletDTO;

import java.util.Date;
import java.util.Objects;

public class Wallet {
    private String _id;
    private String walletName;
    private String currency;
    private String ownerDocument;
    private Date createdAt;

    public static Wallet build() {
        return new Wallet();
    }

    public String getId() {
        return this._id;
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
        return Objects.equals(walletName, wallet.walletName) && Objects.equals(currency, wallet.currency) && Objects.equals(ownerDocument, wallet.ownerDocument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletName, currency, ownerDocument);
    }

    public WalletDTO toDTO() {
        return WalletDTO.build()
                .setCurrency(this.currency)
                .setWalletName(this.walletName);
    }
}
