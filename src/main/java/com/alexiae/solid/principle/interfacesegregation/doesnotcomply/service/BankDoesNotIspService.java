package com.alexiae.solid.principle.interfacesegregation.doesnotcomply.service;

import java.math.BigDecimal;

public interface BankDoesNotIspService {

  void deposit(BigDecimal amount, Long accountId);

  void withdraw(BigDecimal amount, Long accountId);

  void transfer(BigDecimal amount, Long sourceAccountId, Long targetAccountId);

  void generateMonthlyReport(Long accountId);

  void notifyCustomer(Long accountId);
}
