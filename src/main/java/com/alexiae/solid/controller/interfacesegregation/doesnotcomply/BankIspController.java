package com.alexiae.solid.controller.interfacesegregation.doesnotcomply;

import com.alexiae.solid.principle.interfacesegregation.doesnotcomply.service.BankDoesNotIspService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interface-segregation/transactions/does-not-comply")
public class BankIspController {

  @Autowired
  private BankDoesNotIspService bankDoesNotIspService;

  @PostMapping("/deposit")
  public ResponseEntity<String> deposit(@RequestParam BigDecimal amount,
      @RequestParam Long accountId) {
    bankDoesNotIspService.deposit(amount, accountId);
    return ResponseEntity.ok("Deposit successful");
  }

  @PostMapping("/withdraw")
  public ResponseEntity<String> withdraw(@RequestParam BigDecimal amount,
      @RequestParam Long accountId) {
    bankDoesNotIspService.withdraw(amount, accountId);
    return ResponseEntity.ok("Withdraw successful");
  }

  @PostMapping("/transfer")
  public ResponseEntity<String> transfer(@RequestParam BigDecimal amount,
      @RequestParam Long sourceAccountId, @RequestParam Long targetAccountId) {
    bankDoesNotIspService.transfer(amount, sourceAccountId, targetAccountId);
    return ResponseEntity.ok("Transfer successful");
  }

  // Métodos innecesarios en este controlador
  @GetMapping("/report")
  public ResponseEntity<String> generateReport(@RequestParam Long accountId) {
    bankDoesNotIspService.generateMonthlyReport(accountId); // No debería estar aquí
    return ResponseEntity.ok("Report generated");
  }

  @PostMapping("/notify")
  public ResponseEntity<String> notifyCustomer(@RequestParam Long accountId) {
    bankDoesNotIspService.notifyCustomer(accountId); // No debería estar aquí
    return ResponseEntity.ok("Notification sent");
  }
}
