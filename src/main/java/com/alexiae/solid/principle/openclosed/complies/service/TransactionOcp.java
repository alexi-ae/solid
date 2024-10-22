package com.alexiae.solid.principle.openclosed.complies.service;

import com.alexiae.solid.entity.Account;

public interface TransactionOcp {

  void execute(Account account);
}
