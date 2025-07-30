package br.com.pay.wallet.model;

import br.com.pay.wallet.dto.ClientDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "clients")
public class Client {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String phone;
    private String password;
    private String location;

    public static Client newInstance() {
        return new Client();
    }

    public Client() {}

    public Client(String id, String firstName, String lastName, String nickName, String phone, String password, String location) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.phone = phone;
        this.password = password;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public Client setId(String id) {
        this.id = id;
        return this;
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
        return Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(nickName, client.nickName) && Objects.equals(phone, client.phone) && Objects.equals(password, client.password) && Objects.equals(location, client.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, nickName, phone, password, location);
    }

    public ClientDTO toDTO() {
        return ClientDTO.build()
                .setDocument(this.id)
                .setFirstName(this.firstName)
                .setLastName(this.lastName)
                .setNickName(this.nickName)
                .setPhone(this.phone)
                .setLocation(this.location);
    }
}
