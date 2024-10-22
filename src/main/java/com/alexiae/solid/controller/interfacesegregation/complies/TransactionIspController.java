package com.alexiae.solid.controller.interfacesegregation.complies;

import com.alexiae.solid.principle.interfacesegregation.complies.service.TransactionIspService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interface-segregation/transactions/complies")
public class TransactionIspController {

  @Autowired
  private TransactionIspService transactionIspService;

  @PostMapping("/deposit")
  public ResponseEntity<String> deposit(@RequestParam BigDecimal amount,
      @RequestParam Long accountId) {
    transactionIspService.deposit(amount, accountId);
    return ResponseEntity.ok("Deposit successful");
  }

  @PostMapping("/withdraw")
  public ResponseEntity<String> withdraw(@RequestParam BigDecimal amount,
      @RequestParam Long accountId) {
    transactionIspService.withdraw(amount, accountId);
    return ResponseEntity.ok("Withdraw successful");
  }

  @PostMapping("/transfer")
  public ResponseEntity<String> transfer(@RequestParam BigDecimal amount,
      @RequestParam Long sourceAccountId, @RequestParam Long targetAccountId) {
    transactionIspService.transfer(amount, sourceAccountId, targetAccountId);
    return ResponseEntity.ok("Transfer successful");
  }
}
