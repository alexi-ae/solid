package com.alexiae.solid.principle.openclosed.complies.service;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.entity.Transaction;
import com.alexiae.solid.principle.openclosed.complies.TransactionOcpFactory;
import com.alexiae.solid.principle.openclosed.complies.service.TransactionOcp;
import com.alexiae.solid.repository.AccountRepository;
import com.alexiae.solid.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionOcpService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Transactional
  public void processTransaction(String transactionType, Long accountId, BigDecimal amount) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    // Utilizar la fábrica para obtener la transacción adecuada
    TransactionOcp transaction = TransactionOcpFactory.createTransaction(transactionType, amount);

    transaction.execute(account);

    // Guardar la transacción en la base de datos
    Transaction trx = new Transaction();
    trx.setAccount(account);
    trx.setAmount(amount);
    trx.setType(transactionType);
    transactionRepository.save(trx);

    // Guardar la cuenta actualizada después de la transacción
    accountRepository.save(account);
  }
}
