package com.haneen.bankingapp.service;

import com.haneen.bankingapp.dto.AccountDto;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);
}
