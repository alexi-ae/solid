package com.alexiae.solid.principle.interfacesegregation.complies.service;

import java.math.BigDecimal;

public interface TransactionIspService {

  void deposit(BigDecimal amount, Long accountId);

  void withdraw(BigDecimal amount, Long accountId);

  void transfer(BigDecimal amount, Long sourceAccountId, Long targetAccountId);
}
