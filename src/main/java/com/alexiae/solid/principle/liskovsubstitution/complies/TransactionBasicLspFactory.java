package com.alexiae.solid.principle.liskovsubstitution.complies;

import com.alexiae.solid.principle.liskovsubstitution.complies.service.TransactionBasicLsp;
import com.alexiae.solid.principle.liskovsubstitution.complies.service.impl.DepositTransactionLsp;
import com.alexiae.solid.principle.liskovsubstitution.complies.service.impl.WithdrawTransactionLsp;
import java.math.BigDecimal;

public class TransactionBasicLspFactory {

  public static TransactionBasicLsp createTransaction(String transactionType, BigDecimal amount) {
    switch (transactionType.toLowerCase()) {
      case "deposit":
        return new DepositTransactionLsp(amount);
      case "withdraw":
        return new WithdrawTransactionLsp(amount);
      default:
        throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
    }
  }
}
