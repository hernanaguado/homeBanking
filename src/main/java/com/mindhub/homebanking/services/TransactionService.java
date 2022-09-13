package com.mindhub.homebanking.services;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;


public interface TransactionService {

    public void saveTransaction (Transaction transaction);

    public Set<Transaction> getTransactionByDate (LocalDateTime fromDate, LocalDateTime finalDate, Account account);



}
