package com.alexiae.solid.controller.interfacesegregation.complies;

import com.alexiae.solid.principle.interfacesegregation.complies.service.NotificationIspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interface-segregation/notification/complies")
public class NotificationIspController {

  @Autowired
  private NotificationIspService notificationIspService;

  @PostMapping("/notify")
  public ResponseEntity<String> notifyCustomer(@RequestParam Long accountId) {
    notificationIspService.notifyCustomer(accountId);
    return ResponseEntity.ok("Notification sent");
  }

}
