package com.alexiae.solid.principle.liskovsubstitution.complies.service.impl;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.principle.liskovsubstitution.complies.service.TransactionMultipleLsp;
import java.math.BigDecimal;

public class TransferTransactionLsp implements TransactionMultipleLsp {

  private BigDecimal amount;

  public TransferTransactionLsp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account, Account accountDestiny) {
    BigDecimal currentBalanceOrigin = account.getBalance();
    // Valida si el saldo es suficiente de la cuenta de origen
    if (account.getBalance().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    // Actualiza el saldo de la cuenta de origen
    BigDecimal newBalanceOrigin = currentBalanceOrigin.subtract(amount);
    account.setBalance(newBalanceOrigin);

    // Actualiza el saldo de la cuenta destino
    BigDecimal currentBalanceDestiny = accountDestiny.getBalance();
    BigDecimal newBalanceDestiny = currentBalanceDestiny.add(amount);
    accountDestiny.setBalance(newBalanceDestiny);
  }
}
