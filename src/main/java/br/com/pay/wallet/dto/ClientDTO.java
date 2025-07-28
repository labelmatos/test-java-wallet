package br.com.pay.wallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO {
    public String firstName;
    public String lastName;
    public String nickName;
    public String document;
    public String phone;
    public String password;
    public String location;

    public static ClientDTO build() {
        return new ClientDTO();
    }

    public ClientDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ClientDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ClientDTO setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public ClientDTO setDocument(String document) {
        this.document = document;
        return this;
    }

    public ClientDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public ClientDTO setLocation(String location) {
        this.location = location;
        return this;
    }
}
