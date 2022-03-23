/*
Model to hold user id and date of transaction
 */

package com.bob.ets_demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class TransactionDateModel {
    @Id
    @Column(nullable = false)
    private Long id;
    // ideally userId is a foreign key to TableC
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private Timestamp transactionDateTime;

    public TransactionDateModel() {

    }

    public TransactionDateModel(Long id, String userId, Timestamp transactionDateTime) {
        this.id = id;
        this.userId = userId;
        this.transactionDateTime = transactionDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Timestamp transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionDateModel)) return false;
        TransactionDateModel transactionDateModel = (TransactionDateModel) o;
        return Objects.equals(getId(), transactionDateModel.getId()) && Objects.equals(getTransactionDateTime(), transactionDateModel.getTransactionDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTransactionDateTime());
    }
}
