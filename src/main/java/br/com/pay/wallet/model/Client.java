package br.com.pay.wallet.model;

import br.com.pay.wallet.dto.ClientDTO;

import java.util.Objects;

public class Client {
    private String firstName;
    private String lastName;
    private String nickName;
    private String document;
    private String phone;
    private String password;
    private String location;

    public static Client build() {
        return new Client();
    }

    public String getFirstName() {
        return firstName;
    }

    public Client setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Client setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public Client setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getDocument() {
        return document;
    }

    public Client setDocument(String document) {
        this.document = document;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Client setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Client setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Client setLocation(String location) {
        this.location = location;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(nickName, client.nickName) && Objects.equals(document, client.document) && Objects.equals(phone, client.phone) && Objects.equals(password, client.password) && Objects.equals(location, client.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, nickName, document, phone, password, location);
    }

    public ClientDTO toDTO() {
        return ClientDTO.build()
                .setDocument(this.document)
                .setFirstName(this.firstName)
                .setLastName(this.lastName)
                .setNickName(this.nickName)
                .setPhone(this.phone)
                .setLocation(this.location);
                // never return the password
    }
}
