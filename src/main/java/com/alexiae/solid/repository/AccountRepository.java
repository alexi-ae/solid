package com.alexiae.solid.repository;

import com.alexiae.solid.entity.Account;
import com.alexiae.solid.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  List<Account> findByCustomer(Customer customer);
}
