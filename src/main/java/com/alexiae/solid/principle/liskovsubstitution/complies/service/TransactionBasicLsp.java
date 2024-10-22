package com.alexiae.solid.principle.liskovsubstitution.complies.service;

import com.alexiae.solid.entity.Account;

public interface TransactionBasicLsp {

  void execute(Account account);
}
