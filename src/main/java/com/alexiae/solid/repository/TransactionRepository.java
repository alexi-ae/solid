package com.alexiae.solid.repository;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.entity.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByAccount(Account account);
}
