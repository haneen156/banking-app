package com.haneen.bankingapp.service;

import com.haneen.bankingapp.dto.AccountDto;
import com.haneen.bankingapp.dto.CreateAccountRequest;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(CreateAccountRequest request);

    AccountDto getAccountById(Long id);

    List<AccountDto> getAllAccounts();

    AccountDto deposit(Long id, Double amount);

    AccountDto withdraw(Long id, Double amount);

    void deleteAccount(Long id);

    AccountDto updateAccount(Long id, AccountDto updatedAccount);
}
