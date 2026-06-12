package com.haneen.bankingapp.controller;

import com.haneen.bankingapp.dto.*;
import com.haneen.bankingapp.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Accounts", description = "Account Management APIs")
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //ADD new Account
    @PostMapping
    public ResponseEntity<AccountDto> addAccount( @Valid @RequestBody CreateAccountRequest request){
        AccountDto accountDto = accountService.createAccount(request);
        return new ResponseEntity<>(accountDto,HttpStatus.CREATED);
    }

    //find account by id - get
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    //get all accounts
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accountDtoList = accountService.getAllAccounts();
        return ResponseEntity.ok(accountDtoList);
    }

    //deposit (put) money into account
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @Valid @RequestBody DepositRequest request){
        AccountDto accountDto = accountService.deposit(id,request.amount());
        return ResponseEntity.ok(accountDto);
    }

    //withdrew (take) money from account
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @Valid @RequestBody WithdrawRequest request){
        AccountDto accountDto = accountService.withdraw(id,request.amount());
        return ResponseEntity.ok(accountDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account has been deleted successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto updatedAccount){
        AccountDto accountDto = accountService.updateAccount(id,updatedAccount);
        return ResponseEntity.ok(accountDto);
    }
}
