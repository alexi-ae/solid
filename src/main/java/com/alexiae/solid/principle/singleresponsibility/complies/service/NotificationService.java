package com.alexiae.solid.principle.singleresponsibility.complies.service;

import com.alexiae.solid.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  public void sendTransactionNotification(Transaction transaction) {
    // Lógica para enviar una notificación
    System.out.println("Notificación enviada para la transacción: " + transaction.getId());
  }
}
