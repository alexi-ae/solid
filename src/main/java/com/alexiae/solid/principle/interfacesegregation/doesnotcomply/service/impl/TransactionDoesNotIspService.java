package com.alexiae.solid.principle.interfacesegregation.doesnotcomply.service.impl;

import com.alexiae.solid.principle.interfacesegregation.doesnotcomply.service.BankDoesNotIspService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class TransactionDoesNotIspService implements BankDoesNotIspService {

  @Override
  public void deposit(BigDecimal amount, Long accountId) {
    // Lógica para depósito
  }

  @Override
  public void withdraw(BigDecimal amount, Long accountId) {
    // Lógica para retiro
  }

  @Override
  public void transfer(BigDecimal amount, Long sourceAccountId, Long targetAccountId) {
    // Lógica para transferencia
  }

  @Override
  public void generateMonthlyReport(Long accountId) {
    // Este servicio no necesita generar reportes, pero se ve obligado a implementar
    throw new UnsupportedOperationException("Not supported");
  }

  @Override
  public void notifyCustomer(Long accountId) {
    // Este servicio tampoco necesita notificar al cliente
    throw new UnsupportedOperationException("Not supported");
  }
}
