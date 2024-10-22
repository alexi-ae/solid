package com.alexiae.solid.principle.liskovsubstitution.complies.service;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.entity.Transaction;
import com.alexiae.solid.principle.liskovsubstitution.complies.TransactionMultipleLspFactory;
import com.alexiae.solid.repository.AccountRepository;
import com.alexiae.solid.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionMultipleLspService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Transactional
  public void processTransaction(String transactionType, Long accountIdOrigin,
      Long accountIdDestiny, BigDecimal amount) {
    Account accountOrigin = accountRepository.findById(accountIdOrigin)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    Account accountDestiny = accountRepository.findById(accountIdDestiny)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    // Utilizar la fábrica para obtener la transacción adecuada
    TransactionMultipleLsp transaction = TransactionMultipleLspFactory
        .createTransaction(transactionType, amount);

    transaction.execute(accountOrigin, accountDestiny);

    // Guardar la transacción en la base de datos - trx origen
    Transaction trxOrigen = new Transaction();
    trxOrigen.setAccount(accountOrigin);
    trxOrigen.setAmount(amount.negate());
    trxOrigen.setType(transactionType);
    transactionRepository.save(trxOrigen);

    // Guardar la transacción en la base de datos - trx destino
    Transaction trxDestiny = new Transaction();
    trxDestiny.setAccount(accountDestiny);
    trxDestiny.setAmount(amount);
    trxDestiny.setType(transactionType);
    transactionRepository.save(trxDestiny);

    // Guardar la cuenta actualizada después de la transacción
    accountRepository.save(accountOrigin);
    accountRepository.save(accountDestiny);
  }
}
