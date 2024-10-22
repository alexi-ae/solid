package com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.impl;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.TransactionDoesNotLsp;
import java.math.BigDecimal;

public class DepositTransactionDoesNotLsp implements TransactionDoesNotLsp {

  private BigDecimal amount;

  public DepositTransactionDoesNotLsp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal newBalance = account.getBalance().add(amount);
    account.setBalance(newBalance);
  }
}
