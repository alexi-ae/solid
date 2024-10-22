package com.alexiae.solid.principle.openclosed.complies;

import com.alexiae.solid.principle.openclosed.complies.service.TransactionOcp;
import com.alexiae.solid.principle.openclosed.complies.service.impl.DepositTransactionOcp;
import com.alexiae.solid.principle.openclosed.complies.service.impl.WithdrawTransactionOcp;
import java.math.BigDecimal;

public class TransactionOcpFactory {

  public static TransactionOcp createTransaction(String transactionType, BigDecimal amount) {
    switch (transactionType.toLowerCase()) {
      case "deposit":
        return new DepositTransactionOcp(amount);
      case "withdraw":
        return new WithdrawTransactionOcp(amount);
      default:
        throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
    }
  }
}
