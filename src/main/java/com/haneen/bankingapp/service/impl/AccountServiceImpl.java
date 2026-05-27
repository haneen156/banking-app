package com.haneen.bankingapp.service.impl;

import com.haneen.bankingapp.dto.AccountDto;
import com.haneen.bankingapp.entity.Account;
import com.haneen.bankingapp.exception.AccountException;
import com.haneen.bankingapp.mapper.AccountMapper;
import com.haneen.bankingapp.repository.AccountRepository;
import com.haneen.bankingapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountMapper::mapToAccountDto)
                .toList();
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));
        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto updatedAccount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));
        account.setAccountHolderName(updatedAccount.accountHolderName());
        account.setBalance(updatedAccount.balance());
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

}
