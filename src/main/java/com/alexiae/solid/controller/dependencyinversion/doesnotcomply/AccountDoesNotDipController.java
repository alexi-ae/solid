package com.alexiae.solid.controller.dependencyinversion.doesnotcomply;

import com.alexiae.solid.principle.dependencyinversion.doesnotcomply.TransactionDoesNotDipServiceImpl;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dependency-inversion/does-not-comply")
public class AccountDoesNotDipController {

  @Autowired
  private TransactionDoesNotDipServiceImpl transactionService;  // Dependencia directa

  @PostMapping("/deposit")
  public ResponseEntity<String> deposit(@RequestParam BigDecimal amount,
      @RequestParam Long accountId) {
    transactionService.deposit(amount, accountId);
    return ResponseEntity.ok("Deposit successful");
  }

  @PostMapping("/withdraw")
  public ResponseEntity<String> withdraw(@RequestParam BigDecimal amount,
      @RequestParam Long accountId) {
    transactionService.withdraw(amount, accountId);
    return ResponseEntity.ok("Withdraw successful");
  }
}
