package com.haneen.bankingapp.service.impl;

import com.haneen.bankingapp.dto.AccountDto;
import com.haneen.bankingapp.dto.CreateAccountRequest;
import com.haneen.bankingapp.entity.Account;
import com.haneen.bankingapp.exception.AccountException;
import com.haneen.bankingapp.exception.DepositLimitExceededException;
import com.haneen.bankingapp.exception.InsufficientFundsException;
import com.haneen.bankingapp.mapper.AccountMapper;
import com.haneen.bankingapp.repository.AccountRepository;
import com.haneen.bankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Value("${bank.limits.deposit.max}")
    private Double maxDepositLimit;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(CreateAccountRequest request) {
        Account account = AccountMapper.mapToAccount(request);
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
    public AccountDto deposit(Long id, Double amount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));

        if(amount > maxDepositLimit){
            throw new DepositLimitExceededException("Deposit amount exceeds the maximum allowed limit");
        }
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, Double amount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));

        if(account.getBalance() < amount){
            throw new InsufficientFundsException("Insufficient Funds");
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
