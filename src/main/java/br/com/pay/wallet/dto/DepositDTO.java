package br.com.pay.wallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositDTO {
    public double value;
    public String currency;
}
