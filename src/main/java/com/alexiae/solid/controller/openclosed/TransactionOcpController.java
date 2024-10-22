package com.alexiae.solid.controller.openclosed;

import com.alexiae.solid.principle.openclosed.complies.service.TransactionOcpService;
import com.alexiae.solid.principle.openclosed.doesnotcomply.TransactionNotOcpService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open-closed/transactions")
public class TransactionOcpController {

  @Autowired
  private TransactionNotOcpService transactionNotOcpService;

  @Autowired
  private TransactionOcpService transactionOcpService;

  @PostMapping("/does-not-comply")
  public ResponseEntity<String> processDoesNotComply(@RequestParam String type,
      @RequestParam Long accountId,
      @RequestParam BigDecimal amount) {
    transactionNotOcpService.processTransaction(type, accountId, amount);
    return ResponseEntity.ok("Transaction successful");
  }

  @PostMapping("/comply")
  public ResponseEntity<String> processComply(@RequestParam String type,
      @RequestParam Long accountId,
      @RequestParam BigDecimal amount) {
    transactionOcpService.processTransaction(type, accountId, amount);
    return ResponseEntity.ok("Transaction successful");
  }
}
