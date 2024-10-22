package com.alexiae.solid.controller.interfacesegregation.complies;

import com.alexiae.solid.principle.interfacesegregation.complies.service.ReportIspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interface-segregation/report/complies")
public class ReportIspController {

  @Autowired
  private ReportIspService reportIspService;

  @GetMapping("/monthly")
  public ResponseEntity<String> generateMonthlyReport(@RequestParam Long accountId) {
    reportIspService.generateMonthlyReport(accountId);
    return ResponseEntity.ok("Monthly report generated");
  }

}
