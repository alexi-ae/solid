package com.alexiae.solid.principle.singleresponsibility.doesnotcomply;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.entity.Transaction;
import com.alexiae.solid.repository.AccountRepository;
import com.alexiae.solid.repository.TransactionRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionNotSrpService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  public void createTransaction(Long accountId, BigDecimal amount) {
    // 1. Validación dentro del servicio
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("El monto debe ser mayor a 0");
    }

    // 2. Crear transacción
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setAmount(amount);
    transaction.setStatus("CREATED");
    transaction = transactionRepository.save(transaction);

    // 3. Enviar notificación dentro del servicio
    sendNotification(transaction);
  }

  // Método para enviar notificaciones
  private void sendNotification(Transaction transaction) {
    System.out.println("Notificación enviada para la transacción: " + transaction.getId());
  }
}
