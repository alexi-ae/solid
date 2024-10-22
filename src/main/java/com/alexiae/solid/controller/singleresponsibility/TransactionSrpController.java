package com.alexiae.solid.controller.singleresponsibility;

import com.alexiae.solid.principle.singleresponsibility.complies.TransactionSrpService;
import com.alexiae.solid.principle.singleresponsibility.doesnotcomply.TransactionNotSrpService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/single-responsibility/transactions")
public class TransactionSrpController {

  @Autowired
  private TransactionNotSrpService transactionNotSrpService;

  @Autowired
  private TransactionSrpService transactionSrpService;

  @PostMapping("/does-not-comply")
  public ResponseEntity<String> processDoesNotComply(@RequestParam Long accountId,
      @RequestParam BigDecimal amount) {
    transactionNotSrpService.createTransaction(accountId, amount);
    return ResponseEntity.ok("Transaction successful");
  }

  @PostMapping("/comply")
  public ResponseEntity<String> processComply(@RequestParam Long accountId,
      @RequestParam BigDecimal amount) {
    transactionSrpService.createTransaction(accountId, amount);
    return ResponseEntity.ok("Transaction successful");
  }

}
