package br.com.pay.wallet.model;

import java.util.Objects;

public class TransferMetadata {
    private String wallet;
    private String document;
    private String currency;

    public static TransferMetadata build() {
        return new TransferMetadata();
    }

    public String getWallet() {
        return wallet;
    }

    public TransferMetadata setWallet(String wallet) {
        this.wallet = wallet;
        return this;
    }

    public String getDocument() {
        return document;
    }

    public TransferMetadata setDocument(String document) {
        this.document = document;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public TransferMetadata setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransferMetadata that = (TransferMetadata) o;
        return Objects.equals(wallet, that.wallet) && Objects.equals(document, that.document) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wallet, document, currency);
    }
}
