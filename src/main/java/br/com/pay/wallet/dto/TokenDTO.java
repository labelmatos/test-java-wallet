package br.com.pay.wallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDTO {
    public String token;

    public TokenDTO(String token) {
        this.token = token;
    }
}
