package org.example.model;

import java.math.BigDecimal;

public class Transaction {

    public final String hash;
    public final String from;
    public final String to;
    public final String value;
    public final BigDecimal valueQuote;
    public final String feesPaid;

    private Transaction(Builder builder) {
        hash = builder.hash;
        from = builder.from;
        to = builder.to;
        value = builder.value;
        valueQuote = builder.valueQuote;
        feesPaid = builder.feesPaid;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "hash='" + hash + '\'' +
            ", from='" + from + '\'' +
            ", to='" + to + '\'' +
            ", value='" + value + '\'' +
            ", valueQuote=" + valueQuote +
            ", feesPaid='" + feesPaid + '\'' +
            '}';
    }

    public static final class Builder {
        private String hash;
        private String from;
        private String to;
        private String value;
        private BigDecimal valueQuote;
        private String feesPaid;

        public static Builder transaction() {
            return new Builder();
        }

        private Builder() {
        }

        public Builder hash(String hash) {
            this.hash = hash;
            return this;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder valueQuote(BigDecimal valueQuote) {
            this.valueQuote = valueQuote;
            return this;
        }

        public Builder feesPaid(String feesPaid) {
            this.feesPaid = feesPaid;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
