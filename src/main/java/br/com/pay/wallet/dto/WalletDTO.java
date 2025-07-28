package br.com.pay.wallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletDTO {
    public String walletName;
    public String currency;

    public static WalletDTO build() {
        return new WalletDTO();
    }

    public WalletDTO setWalletName(String walletName) {
        this.walletName = walletName;
        return this;
    }

    public WalletDTO setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
