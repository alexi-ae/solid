package com.alexiae.solid.principle.dependencyinversion.doesnotcomply;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class TransactionDoesNotDipServiceImpl {

  public void deposit(BigDecimal amount, Long accountId) {
    // Lógica para depositar dinero en la cuenta
    System.out.println("Depositing " + amount + " to account " + accountId);
  }

  public void withdraw(BigDecimal amount, Long accountId) {
    // Lógica para retirar dinero de la cuenta
    System.out.println("Withdrawing " + amount + " from account " + accountId);
  }
}
