package com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.impl;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.TransactionDoesNotLsp;
import java.math.BigDecimal;

public class WithdrawTransactionDoesNotLsp implements TransactionDoesNotLsp {

  private BigDecimal amount;

  public WithdrawTransactionDoesNotLsp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal currentBalance = account.getBalance();
    if (currentBalance.compareTo(amount) >= 0) {
      BigDecimal newBalance = currentBalance.subtract(amount);
      account.setBalance(newBalance);
    } else {
      throw new IllegalArgumentException("Insufficient balance");
    }
  }
}
