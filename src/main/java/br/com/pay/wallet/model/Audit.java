package br.com.pay.wallet.model;

import br.com.pay.wallet.dto.WalletDTO;

import java.util.Objects;

public class Audit {
    private String walletName;
    private String currency;
    private String ownerDocument;
    private String createdAt;

    public static Audit build() {
        return new Audit();
    }

    public String getWalletName() {
        return walletName;
    }

    public Audit setWalletName(String walletName) {
        this.walletName = walletName;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Audit setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getOwnerDocument() {
        return ownerDocument;
    }

    public Audit setOwnerDocument(String ownerDocument) {
        this.ownerDocument = ownerDocument;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Audit setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Audit wallet = (Audit) o;
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
