package com.marlow.v1.controller;

import com.marlow.v1.model.AccountBalance;
import com.marlow.v1.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/balance/{accountNo}")
    public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable("accountNo") String accountNo) {
        AccountBalance accountBalance = accountService.getAccountBalance(accountNo);

        return ok(accountService.getAccountBalance(accountNo));
    }
}