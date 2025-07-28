package br.com.pay.wallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferDTO {
    public String targetWalletId;
    public String targetDocument;
    public double value;
    public String currency;
}
