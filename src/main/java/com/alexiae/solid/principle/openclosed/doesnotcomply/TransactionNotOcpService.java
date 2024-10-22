package com.alexiae.solid.principle.openclosed.doesnotcomply;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.entity.Transaction;
import com.alexiae.solid.repository.AccountRepository;
import com.alexiae.solid.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionNotOcpService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Transactional
  public void processTransaction(String transactionType, Long accountId, BigDecimal amount) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    switch (transactionType) {
      case "deposit":
        BigDecimal newBalanceDeposit = account.getBalance().add(amount);
        account.setBalance(newBalanceDeposit);
        Transaction txDeposit = new Transaction();
        txDeposit.setAccount(account);
        txDeposit.setAmount(amount);
        txDeposit.setType(transactionType);
        transactionRepository.save(txDeposit);
        break;
      case "withdraw":
        if (account.getBalance().compareTo(amount) >= 0) {
          BigDecimal newBalanceWithdraw = account.getBalance().subtract(amount);
          account.setBalance(newBalanceWithdraw);
          Transaction txWithdraw = new Transaction();
          txWithdraw.setAccount(account);
          txWithdraw.setAmount(amount);
          txWithdraw.setType(transactionType);
          transactionRepository.save(txWithdraw);
        } else {
          throw new IllegalArgumentException("Insufficient balance");
        }
        break;
      case "transfer":
        // Supongamos que tenemos otra cuenta y queremos transferir a esa cuenta.
        // Este código es solo un ejemplo. Aquí deberías implementar la lógica de transferencia.
        throw new UnsupportedOperationException("Transfer operation is not implemented yet.");
      default:
        throw new UnsupportedOperationException("Transaction type not supported");
    }

    accountRepository.save(account); // Persist the account after transaction
  }
}
