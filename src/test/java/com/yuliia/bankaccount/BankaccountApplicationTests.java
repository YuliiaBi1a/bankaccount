package com.yuliia.bankaccount;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankaccountApplicationTests {

	private SavingsAccount savingsAccount;
	private CheckingAccount checkingAccount;

	@BeforeEach
	void setUp() {
		savingsAccount = new SavingsAccount(15000, 4);
		checkingAccount = new CheckingAccount(10000, 4);
	}

	@Test
	void testSavingsAccountDeposit() {
		savingsAccount.deposit(5000);
		assertEquals(20000, savingsAccount.getBalance());
		assertEquals(1, savingsAccount.getNumberOfDeposits());
	}

	@Test
	void testSavingsAccountWithdraw() {
		savingsAccount.withdraw(3000);
		assertEquals(12000, savingsAccount.getBalance());
		assertEquals(1, savingsAccount.getNumberOfWithdrawals());
	}

	@Test
	void testSavingsAccountInactiveDeposit() {
		SavingsAccount savingsAccount = new SavingsAccount(11000, 0.02f);
		savingsAccount.withdraw(2000);
		assertFalse(savingsAccount.isActive(), "Account should be inactive.");

		savingsAccount.deposit(2000); // Attempt to deposit
		assertEquals(9000, savingsAccount.getBalance(), "Balance should not change for inactive account.");
		assertEquals(0, savingsAccount.getNumberOfDeposits(), "No deposits should be counted.");
	}

	@Test
	void testSavingsAccountMonthlyStatement() {
		// Balance inicial: 15000
		// 4% anual = 0.04 / 12 = 0.0033 mensual
		savingsAccount.deposit(5000); // Balance: 20000
		savingsAccount.withdraw(3000);
		savingsAccount.withdraw(2000);
		savingsAccount.withdraw(2000);
		savingsAccount.withdraw(1000);
		savingsAccount.withdraw(1000); // Balance: 11000, retiro 5 (multa por exceso de retiros)

		savingsAccount.monthlyStatement();

		// Cálculo del balance esperado:
		// 1. Balance después de 5 retiros: 11000
		// 2. Multa por exceso de retiros (1 retiro más del límite = 5-4 * 1000): 1000
		//    Balance después de la multa: 11000 - 1000 = 10000
		// 3. Adición de los intereses mensuales:
		//    Tasa mensual: 4% anual -> 4 / 12 = 0.3333% mensual
		//    Intereses: 10000 * 0.003333 = 33.33
		//    Balance final: 10000 + 33.33 = 10033.33

		assertEquals(10033.33, savingsAccount.getBalance(), 0.01, "After all operations balance should be equals to 10033.33");
		assertTrue(savingsAccount.isActive());
	}

	@Test
	void testSavingsAccountPrint() {
		savingsAccount.print();
	}

	@Test
	void testCheckingAccountWithdrawWithinBalance() {
		checkingAccount.withdraw(5000);
		assertEquals(5000, checkingAccount.getBalance());
		assertEquals(1, checkingAccount.getNumberOfWithdrawals());
		assertEquals(0, checkingAccount.getOverdraft());
	}

	@Test
	void testCheckingAccountWithdrawWithOverdraft() {
		checkingAccount.withdraw(15000);
		assertEquals(0, checkingAccount.getBalance());
		assertEquals(1, checkingAccount.getNumberOfWithdrawals());
		assertEquals(5000, checkingAccount.getOverdraft());
	}

	@Test
	void testCheckingAccountDepositToClearOverdraft() {
		checkingAccount.withdraw(15000); // Creates overdraft 10 000 - 15 000
		checkingAccount.deposit(7000); // Clears part of the overdraft -5 000 + 7 000
		assertEquals(2000, checkingAccount.getBalance());
		assertEquals(0, checkingAccount.getOverdraft());
	}

	@Test
	void testCheckingAccountDepositToReduceOverdraft() {
		checkingAccount.withdraw(15000);
		checkingAccount.deposit(3000); // overdraft -5 000 + 3 000
		assertEquals(0, checkingAccount.getBalance());
		assertEquals(2000, checkingAccount.getOverdraft());
	}
	@Test
	void testCheckingAccountMonthlyStatement() {
		checkingAccount.withdraw(15000);
		checkingAccount.monthlyStatement();

		assertEquals(0, checkingAccount.getBalance());
		assertEquals(5000, checkingAccount.getOverdraft());
	}

	@Test
	void testCheckingAccountPrint() {
		checkingAccount.print();
	}
}

