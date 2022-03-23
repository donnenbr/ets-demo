/*
model to hold userid, transaction date, and amount of transaction
 */
package com.bob.ets_demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class TransactionModel {
    @Id
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private Timestamp transactionDateTime;
    @Column(nullable = false)
    private BigDecimal transactionAmount;

    public TransactionModel() {

    }

    public TransactionModel(Long id, String userId, Timestamp transactionDateTime, BigDecimal transactionAmount) {
        this.id = id;
        this.userId = userId;
        this.transactionDateTime = transactionDateTime;
        this.transactionAmount = transactionAmount;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getTransactionDateTime() {
        return transactionDateTime;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTransactionDateTime(Timestamp transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String toString() {
        return "{id=" + id + ", userId=" + userId + ", trans date=" + transactionDateTime + ", trans amt=" + transactionAmount + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionModel)) return false;
        TransactionModel transactionModel = (TransactionModel) o;
        return Objects.equals(getId(), transactionModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
