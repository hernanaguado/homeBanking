package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private Double balance;
    private LocalDateTime creationDate;
    private Set<TransactionDTO> transactions;
    private boolean accountState;

    private AccountType accountType;

    public AccountDTO () {}
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.creationDate = account.getCreationDate();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.accountState= account.isAccountState();
        this.accountType= account.getAccountType();


    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Double getBalance() {
        return balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Set<TransactionDTO> getTransactions() { return transactions; }

    public boolean isAccountState() {   return accountState;}

    public AccountType getAccountType() {return accountType;}
}


