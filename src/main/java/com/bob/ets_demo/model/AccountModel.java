/*
model to hold user id and total amount of account
 */
package com.bob.ets_demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class AccountModel {
    @Id
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private BigDecimal totalAmount;

    public AccountModel() {

    }

    public AccountModel(String userId, BigDecimal totalAmount) throws Exception {
        checkTotalAmount(totalAmount);
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) throws Exception {
        checkTotalAmount(totalAmount);
        this.totalAmount = totalAmount;
    }

    private void checkTotalAmount(BigDecimal totalAmount) throws Exception {
        if (totalAmount == null) {
            throw new IllegalArgumentException("total amount cannot be null");
        }
        if (totalAmount.doubleValue() < 0) {
            throw new IllegalArgumentException("total amount cannot less than zero");
        }
    }

    public String toString() {
        return "User ID " + this.userId + ", total amount " + this.totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountModel)) return false;
        AccountModel accountModel = (AccountModel) o;
        return Objects.equals(getUserId(), accountModel.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}
