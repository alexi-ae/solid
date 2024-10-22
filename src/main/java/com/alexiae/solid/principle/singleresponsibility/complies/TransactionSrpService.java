package com.alexiae.solid.principle.singleresponsibility.complies;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.entity.Transaction;
import com.alexiae.solid.principle.singleresponsibility.complies.service.NotificationService;
import com.alexiae.solid.principle.singleresponsibility.complies.service.TransactionValidationService;
import com.alexiae.solid.repository.AccountRepository;
import com.alexiae.solid.repository.TransactionRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionSrpService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionValidationService validationService;

  @Autowired
  private NotificationService notificationService;

  public void createTransaction(Long accountId, BigDecimal amount) {
    // 1. Validar la transacción usando un servicio de validación
    validationService.validateTransaction(amount);

    // 2. Crear la transacción
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setAmount(amount);
    transaction.setStatus("CREATED");
    transaction = transactionRepository.save(transaction);

    // 3. Enviar notificación usando un servicio de notificación
    notificationService.sendTransactionNotification(transaction);
  }

}
