package com.haneen.bankingapp.mapper;

import com.haneen.bankingapp.dto.AccountDto;
import com.haneen.bankingapp.dto.CreateAccountRequest;
import com.haneen.bankingapp.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(CreateAccountRequest request){
        Account account = new Account(
                request.accountHolderName(),
                request.initialBalance()
        );
        return account;
    }

    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;
    }
}
