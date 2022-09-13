package com.mindhub.homebanking.services.Implementations;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImplementation implements LoanService {

    @Autowired
    public LoanRepository loanRepository;

    @Override
    public Loan getLoanById (long id) {return loanRepository.findById(id);}

    @Override
    public Loan getLoanByName (String name) {return loanRepository.findByName(name);}

    @Override
    public List<Loan> getAllLoans(){ return loanRepository.findAll();}

    @Override
    public void saveLoan(Loan loan){
        loanRepository.save(loan);
    }



}
