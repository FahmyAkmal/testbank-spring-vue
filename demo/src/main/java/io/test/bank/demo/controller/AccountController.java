package io.test.bank.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.test.bank.demo.repository.AccountRepository;
import io.test.bank.demo.model.Account;

@CrossOrigin
@RestController
@RequestMapping("/api")

public class AccountController {
    
    @Autowired
    AccountRepository repository;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(required = false) String accountType) {
        try {
            List<Account> accounts = new ArrayList<Account>();
            if (accountType == null)
            repository.findAll().forEach(accounts::add);
            else
            repository.findByAccountType(accountType).forEach(accounts::add);
            if (accounts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountsById(@PathVariable("id") long id) {
        Optional<Account> accountData = repository.findById(id);
        if (accountData.isPresent()) {
            return new ResponseEntity<>(accountData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccounts(@RequestBody Account account) {
        try {
            Account _account = repository
                    .save(new Account(account.getAccountType(), account.getAccountBalance()));
            return new ResponseEntity<>(_account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccounts(@PathVariable("id") long id, @RequestBody Account account) {
        Optional<Account> accountData = repository.findById(id);
        if (accountData.isPresent()) {
            Account _account = accountData.get();
            _account.setAccountType(account.getAccountType());
            _account.setAccountBalance(account.getAccountBalance());
            return new ResponseEntity<>(repository.save(_account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<HttpStatus> deleteAccounts(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}