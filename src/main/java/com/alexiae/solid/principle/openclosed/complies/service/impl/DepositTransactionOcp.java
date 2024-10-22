package com.alexiae.solid.principle.openclosed.complies.service.impl;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.principle.openclosed.complies.service.TransactionOcp;
import java.math.BigDecimal;

public class DepositTransactionOcp implements TransactionOcp {

  private BigDecimal amount;

  public DepositTransactionOcp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal newBalance = account.getBalance().add(amount);
    account.setBalance(newBalance);
  }
}
