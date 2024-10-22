package com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.entity.Transaction;
import com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.TransactionDoesNotLspFactory;
import com.alexiae.solid.repository.AccountRepository;
import com.alexiae.solid.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDoesNotLspService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Transactional
  public void processTransaction(String transactionType, Long accountIdOrigin,
      Long accountIdDestiny, BigDecimal amount) {
    Account account = accountRepository.findById(accountIdOrigin)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    // obtiene cuenta destino si es de tipo transfer
    Account accountDestiny = null;
    if ("transfer".equalsIgnoreCase(transactionType)) {
      accountDestiny = accountRepository.findById(accountIdOrigin)
          .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    }

    // Utilizar la fábrica para obtener la transacción adecuada
    // para este caso necesita la cuenta destino, aunque no todos lo usen
    TransactionDoesNotLsp transaction = TransactionDoesNotLspFactory
        .createTransaction(transactionType, amount, accountDestiny);

    transaction.execute(account);

    // Guardar la transacción en la base de datos - trx origen
    Transaction trxOrigen = new Transaction();
    trxOrigen.setAccount(account);
    trxOrigen.setAmount(amount.negate());
    trxOrigen.setType(transactionType);
    transactionRepository.save(trxOrigen);

    // Guardar la transacción en la base de datos - trx destino
    Transaction trxDestiny = new Transaction();
    trxDestiny.setAccount(account);
    trxDestiny.setAmount(amount);
    trxDestiny.setType(transactionType);
    transactionRepository.save(trxDestiny);

    // Guardar la cuenta actualizada después de la transacción
    accountRepository.save(account);
    accountRepository.save(accountDestiny);
  }
}
