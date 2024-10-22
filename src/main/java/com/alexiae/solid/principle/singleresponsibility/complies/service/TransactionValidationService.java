package com.alexiae.solid.principle.singleresponsibility.complies.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class TransactionValidationService {

  public void validateTransaction(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("El monto debe ser mayor a 0");
    }
  }
}
