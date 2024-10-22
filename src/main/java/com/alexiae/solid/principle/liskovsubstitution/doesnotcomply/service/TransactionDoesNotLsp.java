package com.alexiae.solid.principle.liskovsubstitution.doesnotcomply.service;

import com.alexiae.solid.entity.Account;

public interface TransactionDoesNotLsp {

  void execute(Account account);
}
