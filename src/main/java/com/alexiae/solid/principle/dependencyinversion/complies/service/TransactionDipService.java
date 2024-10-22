package com.alexiae.solid.principle.dependencyinversion.complies.service;

import java.math.BigDecimal;

public interface TransactionDipService {

  void deposit(BigDecimal amount, Long accountId);

  void withdraw(BigDecimal amount, Long accountId);
}
