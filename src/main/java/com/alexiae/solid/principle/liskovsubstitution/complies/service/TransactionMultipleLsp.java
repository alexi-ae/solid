package com.alexiae.solid.principle.liskovsubstitution.complies.service;

import com.alexiae.solid.entity.Account;

public interface TransactionMultipleLsp {

  void execute(Account accountOrigin, Account accountDestiny);
}
