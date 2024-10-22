package com.alexiae.solid.principle.liskovsubstitution.complies;

import com.alexiae.solid.principle.liskovsubstitution.complies.service.TransactionMultipleLsp;
import com.alexiae.solid.principle.liskovsubstitution.complies.service.impl.TransferTransactionLsp;
import java.math.BigDecimal;

public class TransactionMultipleLspFactory {

  public static TransactionMultipleLsp createTransaction(String transactionType,
      BigDecimal amount) {
    switch (transactionType.toLowerCase()) {
      case "trasnfer":
        return new TransferTransactionLsp(amount);
      default:
        throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
    }
  }
}
