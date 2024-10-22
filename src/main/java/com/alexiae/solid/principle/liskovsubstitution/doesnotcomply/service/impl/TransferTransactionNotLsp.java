package com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.impl;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.TransactionDoesNotLsp;
import java.math.BigDecimal;

public class TransferTransactionNotLsp implements TransactionDoesNotLsp {

  private BigDecimal amount;

  private Account accountTarget;

  public TransferTransactionNotLsp(BigDecimal amount, Account accountTarget) {
    this.amount = amount;
    this.accountTarget = accountTarget;
  }

  @Override
  public void execute(Account account) {
    BigDecimal currentBalanceOrigin = account.getBalance();
    // Valida si el saldo es suficiente de la cuenta de origen
    if (account.getBalance().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    // Actualiza el saldo de la cuenta de origen
    BigDecimal newBalanceOrigin = currentBalanceOrigin.subtract(amount);
    account.setBalance(newBalanceOrigin);

    // Actualiza el saldo de la cuenta destino
    BigDecimal currentBalanceDestiny = accountTarget.getBalance();
    BigDecimal newBalanceDestiny = currentBalanceDestiny.add(amount);
    accountTarget.setBalance(newBalanceDestiny);
  }
}
