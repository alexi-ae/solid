package com.alexiae.solid.principle.interfacesegregation.complies.service.impl;

import com.alexiae.solid.principle.interfacesegregation.complies.service.ReportIspService;
import org.springframework.stereotype.Service;

@Service
public class ReportIspServiceImpl implements ReportIspService {

  @Override
  public void generateMonthlyReport(Long accountId) {
    // Lógica para generar reporte
  }
}
