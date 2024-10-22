package com.alexiae.solid.principle.liskovsubstitution.complies.service.impl;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.principle.liskovsubstitution.complies.service.TransactionBasicLsp;
import java.math.BigDecimal;

public class DepositTransactionLsp implements TransactionBasicLsp {

  private BigDecimal amount;

  public DepositTransactionLsp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal newBalance = account.getBalance().add(amount);
    account.setBalance(newBalance);
  }
}
