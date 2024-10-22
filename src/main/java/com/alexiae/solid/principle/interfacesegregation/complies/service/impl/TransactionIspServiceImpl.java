package com.alexiae.solid.principle.interfacesegregation.complies.service.impl;

import com.alexiae.solid.principle.interfacesegregation.complies.service.TransactionIspService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class TransactionIspServiceImpl implements TransactionIspService {

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
}
