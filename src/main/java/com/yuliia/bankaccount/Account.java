package com.yuliia.bankaccount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
abstract class Account {
    protected float balance;
    protected int numberOfDeposits = 0;
    protected int numberOfWithdrawals = 0;
    protected float annualInterestRate;
    protected float monthlyFee = 0;

    public Account(float initialBalance, float annualInterestRate) {
        this.balance = initialBalance;
        this.annualInterestRate = annualInterestRate;
    }

    public void deposit(float amount) {
        balance += amount;
        numberOfDeposits++;
    }

    public void withdraw(float amount) {
        if (amount <= balance) {
            balance -= amount;
            numberOfWithdrawals++;
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void calculateMonthlyInterest() {
        float monthlyInterest = (annualInterestRate / 12) / 100 * balance;
        balance += monthlyInterest;
    }

    public void monthlyStatement() {
        balance -= monthlyFee;
        calculateMonthlyInterest();
    }

    public abstract void print();
}

