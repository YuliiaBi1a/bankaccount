package com.yuliia.bankaccount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
class SavingsAccount extends Account {
    private boolean active;

    public SavingsAccount(float initialBalance, float annualInterestRate) {
        super(initialBalance, annualInterestRate);
        updateStatus();
    }

    private void updateStatus() {
        active = balance >= 10000;
    }

    @Override
    public void deposit(float amount) {
        if (!active) {
            System.out.println("The account is inactive. Deposit not allowed.");
            return;
        }
        super.deposit(amount);
        updateStatus();
    }

    @Override
    public void withdraw(float amount) {
        if (!active) {
            System.out.println("The account is inactive. Withdrawal not allowed.");
            return;
        }
        super.withdraw(amount);
        updateStatus();
    }

    @Override
    public void monthlyStatement() {
        if (numberOfWithdrawals > 4) {
            monthlyFee += (numberOfWithdrawals - 4) * 1000;
        }
        super.monthlyStatement();
        updateStatus();
    }

    @Override
    public void print() {
        System.out.println("Savings Account:");
        System.out.printf("Balance: %.2f, Monthly Fee: %.2f, Transactions: %d\n",
                balance, monthlyFee, numberOfDeposits + numberOfWithdrawals);
        System.out.printf("Active: " + this.active);
    }


}
