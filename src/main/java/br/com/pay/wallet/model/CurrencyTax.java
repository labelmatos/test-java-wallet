package br.com.pay.wallet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "currencyTaxes")
public class CurrencyTax {
    @Id
    private String id;
    private String pair;
    private Double rate;

    public CurrencyTax(String id, String pair, Double rate) {
        this.id = id;
        this.pair = pair;
        this.rate = rate;
    }

    public CurrencyTax() {}

    public static CurrencyTax build() {
        return new CurrencyTax();
    }

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        this.id = id;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyTax that = (CurrencyTax) o;
        return Double.compare(rate, that.rate) == 0 && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rate);
    }
}
