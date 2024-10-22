package com.alexiae.solid.controller.liskovsubstitution;

import com.alexiae.solid.principle.liskovsubstitution.complies.service.TransactionBasicLspService;
import com.alexiae.solid.principle.liskovsubstitution.complies.service.TransactionMultipleLspService;
import com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service.TransactionDoesNotLspService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/liskov-substitution/transactions")
public class TransactionLspController {

  @Autowired
  private TransactionDoesNotLspService transactionDoesNotLspService;

  @Autowired
  private TransactionBasicLspService transactionBasicLspService;

  @Autowired
  private TransactionMultipleLspService transactionMultipleLspService;


  @PostMapping("/does-not-comply")
  public ResponseEntity<String> processDoesNotComply(@RequestParam String type,
      @RequestParam Long accountIdOrigin,
      @RequestParam Long accountIdDestiny,
      @RequestParam BigDecimal amount) {
    transactionDoesNotLspService
        .processTransaction(type, accountIdOrigin, accountIdDestiny, amount);
    return ResponseEntity.ok("Transaction successful");
  }

  @PostMapping("/comply/basic")
  public ResponseEntity<String> processComplyBasic(@RequestParam String type,
      @RequestParam Long accountId,
      @RequestParam BigDecimal amount) {
    transactionBasicLspService.processTransaction(type, accountId, amount);
    return ResponseEntity.ok("Transaction successful");
  }

  @PostMapping("/comply/multiple")
  public ResponseEntity<String> processComplyMultiple(@RequestParam String type,
      @RequestParam Long accountIdOrigin,
      @RequestParam Long accountIdDestiny,
      @RequestParam BigDecimal amount) {
    transactionMultipleLspService
        .processTransaction(type, accountIdOrigin, accountIdDestiny, amount);
    return ResponseEntity.ok("Transaction successful");
  }

}
