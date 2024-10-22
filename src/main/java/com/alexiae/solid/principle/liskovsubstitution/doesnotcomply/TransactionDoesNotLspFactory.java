package com.alexiae.solid.principle.liskovsubstitution.doesnotcomply;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.TransactionDoesNotLsp;
import com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.impl.DepositTransactionDoesNotLsp;
import com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.impl.TransferTransactionNotLsp;
import com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.impl.WithdrawTransactionDoesNotLsp;
import java.math.BigDecimal;

public class TransactionDoesNotLspFactory {

  // el factory no utiliza todos los parametros para los servicios especificos
  // solo se necesita la cuenta destino cuando el tipo de trx es transfer
  public static TransactionDoesNotLsp createTransaction(String transactionType, BigDecimal amount,
      Account account) {
    switch (transactionType.toLowerCase()) {
      case "deposit":
        return new DepositTransactionDoesNotLsp(amount);
      case "withdraw":
        return new WithdrawTransactionDoesNotLsp(amount);
      case "transfer":
        return new TransferTransactionNotLsp(amount, account);
      default:
        throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
    }
  }
}
