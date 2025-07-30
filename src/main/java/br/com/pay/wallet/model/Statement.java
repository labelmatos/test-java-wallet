package br.com.pay.wallet.model;

import br.com.pay.wallet.dto.BalanceDTO;
import br.com.pay.wallet.dto.StatementDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "statements")
public class Statement {
    @Id
    private String id;
    private String walletId;
    private double value;
    private String currency;
    private double previousValue;
    private String operation;
    private double finalValue;
    private TransferMetadata from;
    private TransferMetadata to;
    private int searchDate;
    private Date createdAt;

    public static Statement newInstance() {
        return new Statement();
    }

    public String getId() {
        return id;
    }

    public Statement setId(String id) {
        this.id = id;
        return this;
    }

    public String getWalletId() {
        return walletId;
    }

    public Statement setWalletId(String walletId) {
        this.walletId = walletId;
        return this;
    }

    public double getValue() {
        return value;
    }

    public Statement setValue(double value) {
        this.value = value;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Statement setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public double getPreviousValue() {
        return previousValue;
    }

    public Statement setPreviousValue(double previousValue) {
        this.previousValue = previousValue;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public Statement setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public double getFinalValue() {
        return finalValue;
    }

    public Statement setFinalValue(double finalValue) {
        this.finalValue = finalValue;
        return this;
    }

    public TransferMetadata getFrom() {
        return from;
    }

    public Statement setFrom(TransferMetadata from) {
        this.from = from;
        return this;
    }

    public TransferMetadata getTo() {
        return to;
    }

    public Statement setTo(TransferMetadata to) {
        this.to = to;
        return this;
    }

    public int getSearchDate() {
        return searchDate;
    }

    public Statement setSearchDate(int searchDate) {
        this.searchDate = searchDate;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Statement setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Statement statement = (Statement) o;
        return Double.compare(value, statement.value) == 0 && Double.compare(previousValue, statement.previousValue) == 0 && Double.compare(finalValue, statement.finalValue) == 0 && Objects.equals(walletId, statement.walletId) && Objects.equals(currency, statement.currency) && Objects.equals(operation, statement.operation) && Objects.equals(searchDate, statement.searchDate) && Objects.equals(createdAt, statement.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, value, currency, previousValue, operation, finalValue, searchDate, createdAt);
    }

    public BalanceDTO toBalanceDTO(Wallet wallet) {
        return BalanceDTO.build()
                .setWalletId(this.walletId)
                .setWalletName(wallet.getWalletName())
                .setBalance(this.finalValue)
                .setCurrency(wallet.getCurrency());
    }

    public StatementDTO toDTO() {
        return StatementDTO.build()
                .setWalletId(this.walletId)
                .setPreviousValue(this.previousValue)
                .setValue(this.value)
                .setOperation(this.operation)
                .setFinalValue(this.finalValue)
                .setCreatedAt(this.createdAt);
    }
}
