package io.test.bank.demo.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data

public class Account{
    @Id
    @GeneratedValue
    private long id;
    private String accountType;
    private Integer accountBalance;

    public Account() {
	}

    public Account(String accountType, Integer accountBalance) {
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	
	}

    public long getId() {
        return id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Integer accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", AccountType=" + accountType + ", AccountBalance=" + accountBalance + "]";
    }
}
