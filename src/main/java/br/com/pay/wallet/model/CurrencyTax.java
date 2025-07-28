package br.com.pay.wallet.model;

import java.util.Objects;

public class CurrencyTax {
    private String _id;
    private double rate;

    public static CurrencyTax build() {
        return new CurrencyTax();
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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
        return Double.compare(rate, that.rate) == 0 && Objects.equals(_id, that._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, rate);
    }
}
