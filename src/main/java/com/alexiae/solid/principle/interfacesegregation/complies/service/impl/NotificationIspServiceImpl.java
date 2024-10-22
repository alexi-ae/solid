package com.alexiae.solid.principle.interfacesegregation.complies.service.impl;

import com.alexiae.solid.principle.interfacesegregation.complies.service.NotificationIspService;
import org.springframework.stereotype.Service;

@Service
public class NotificationIspServiceImpl implements NotificationIspService {

  @Override
  public void notifyCustomer(Long accountId) {
    // Lógica para notificar al cliente
  }
}
