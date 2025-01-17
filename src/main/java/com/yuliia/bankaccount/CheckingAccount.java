package com.yuliia.bankaccount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
class CheckingAccount extends Account {
    private float overdraft = 0;

    public CheckingAccount(float initialBalance, float annualInterestRate) {
        super(initialBalance, annualInterestRate);
    }

    @Override
    public void withdraw(float amount) {
        if (amount <= balance) {
            super.withdraw(amount);
        } else {
            float deficit = amount - balance;
            balance = 0;
            overdraft += deficit;
            numberOfWithdrawals++;
        }
    }

    @Override
    public void deposit(float amount) {
        if (overdraft > 0) {
            float remaining = amount - overdraft;
            if (remaining >= 0) {
                overdraft = 0;
                balance += remaining;
            } else {
                overdraft -= amount;
            }
        } else {
            balance += amount;
        }
    }

    @Override
    public void monthlyStatement() {
        super.monthlyStatement();
    }

    @Override
    public void print() {
        System.out.println("Checking Account:");
        System.out.printf("Balance: %.2f, Monthly Fee: %.2f, Transactions: %d, Overdraft: %.2f\n",
                balance, monthlyFee, numberOfDeposits + numberOfWithdrawals, overdraft);
    }
}

