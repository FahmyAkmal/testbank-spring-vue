package io.test.bank.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.test.bank.demo.model.Account;


public interface AccountRepository extends JpaRepository<Account, Long> {
  List<Account> findByAccountType(String accountType);
}