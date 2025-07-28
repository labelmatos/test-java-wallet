package br.com.pay.wallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceDTO {
    public double balance;
    public String walletId;
    public String walletName;
    public String currency;

    public static BalanceDTO build() {
        return new BalanceDTO();
    }

    public BalanceDTO setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public BalanceDTO setWalletId(String walletId) {
        this.walletId = walletId;
        return this;
    }

    public BalanceDTO setWalletName(String walletName) {
        this.walletName = walletName;
        return this;
    }

    public BalanceDTO setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
