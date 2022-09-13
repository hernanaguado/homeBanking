package com.mindhub.homebanking.services.Implementations;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplementation implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public void saveTransaction(Transaction transaction){transactionRepository.save(transaction);}

    @Override
    public Set<Transaction> getTransactionByDate(LocalDateTime fromDate, LocalDateTime finalDate,Account account) {

        return transactionRepository.findByDateBetween(fromDate,finalDate).stream().filter(transaction -> transaction.getAccount()==account).collect(Collectors.toSet());

    }
}
