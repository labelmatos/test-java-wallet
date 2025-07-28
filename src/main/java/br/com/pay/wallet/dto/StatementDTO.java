package br.com.pay.wallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatementDTO {
    public String walletId;
    public double value;
    public double previousValue;
    public String operation;
    public double finalValue;
    public Date createdAt;

    public static StatementDTO build() {
        return new StatementDTO();
    }

    public StatementDTO setWalletId(String walletId) {
        this.walletId = walletId;
        return this;
    }

    public StatementDTO setValue(double value) {
        this.value = value;
        return this;
    }

    public StatementDTO setPreviousValue(double previousValue) {
        this.previousValue = previousValue;
        return this;
    }

    public StatementDTO setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public StatementDTO setFinalValue(double finalValue) {
        this.finalValue = finalValue;
        return this;
    }

    public StatementDTO setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
