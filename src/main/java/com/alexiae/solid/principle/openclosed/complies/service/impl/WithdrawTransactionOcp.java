package com.alexiae.solid.principle.openclosed.complies.service.impl;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.principle.openclosed.complies.service.TransactionOcp;
import java.math.BigDecimal;

public class WithdrawTransactionOcp implements TransactionOcp {

  private BigDecimal amount;

  public WithdrawTransactionOcp(BigDecimal amount) {
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
