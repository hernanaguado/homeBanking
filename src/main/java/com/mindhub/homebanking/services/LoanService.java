package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface LoanService {

    public Loan getLoanById (long id);

    public Loan getLoanByName (String name);

    public List<Loan> getAllLoans ();

    public void saveLoan (Loan loan);
}
