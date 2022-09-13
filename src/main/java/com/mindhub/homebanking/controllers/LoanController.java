package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


@RestController
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Transactional // volver al pto de inicio x si hay algun error
    @RequestMapping(path="/api/client/loans", method= RequestMethod.POST)
    public ResponseEntity<Object> addLoan(@RequestBody LoanAplicationDTO loanAplicationDTO, Authentication authentication) {

        Client currentClientAuthentication = clientService.getClientByEmail(authentication.getName());
        Loan loanClientAuthentication = loanService.getLoanById(loanAplicationDTO.getId());
        Account accountLoan = accountService.getAccountByNumber(loanAplicationDTO.getAccountDestination());

        if ( loanAplicationDTO.getAmount() <= 0 || loanAplicationDTO.getPayments() <= 0 ) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

    }

        if (loanAplicationDTO.getAccountDestination().isEmpty()) {

            return new ResponseEntity<>("Missing Account Destination", HttpStatus.FORBIDDEN);

        }

        if (accountLoan == null) {

            return new ResponseEntity<>("account dosen't exist", HttpStatus.FORBIDDEN);

        }

        if (loanAplicationDTO.getAmount() > loanClientAuthentication.getMaxAmount()) {

        return new ResponseEntity<>("Amount limit exceeded", HttpStatus.FORBIDDEN);

    }

        if (!loanClientAuthentication.getPayments().contains(loanAplicationDTO.getPayments())) {

            return new ResponseEntity<>("Payments dosent allowed", HttpStatus.FORBIDDEN);

        }

        if (accountService.getAccountByNumber(loanAplicationDTO.getAccountDestination()) == null) {

            return new ResponseEntity<>("destiny account dosen't exist", HttpStatus.FORBIDDEN);

        }

        if (!currentClientAuthentication.getAccounts().contains(accountService.getAccountByNumber(accountLoan.getNumber()))) {

            return new ResponseEntity<>("Destiny account dosent match with client user", HttpStatus.FORBIDDEN);

        }



            Transaction transactionLoan = new Transaction(accountLoan,TransactionType.CREDIT,loanAplicationDTO.getAmount(),loanClientAuthentication.getName() + " Loan approved",LocalDateTime.now());
            double interestPlus =((loanAplicationDTO.getAmount())*0.2) + loanAplicationDTO.getAmount();

            transactionService.saveTransaction(transactionLoan);
            accountLoan.setBalance(accountLoan.getBalance()+loanAplicationDTO.getAmount());

            ClientLoan loanPetition = new ClientLoan(interestPlus,loanAplicationDTO.getPayments(),currentClientAuthentication,loanClientAuthentication);
            clientLoanService.saveClientLoan(loanPetition);

        switch (loanClientAuthentication.getName()){
            case "Personal":
                switch (loanPetition.getPayments()){
                    case 6: loanPetition.setAmount(loanPetition.getAmount() * 1.20);
                        break;
                    case 12: loanPetition.setAmount(loanPetition.getAmount() * 1.30);
                        break;
                    case 24: loanPetition.setAmount(loanPetition.getAmount() * 1.40);
                        break;
                }
                break;
            case "Hipotecario":
                switch (loanPetition.getPayments()){
                    case 12: loanPetition.setAmount(loanPetition.getAmount() * 1.30);
                        break;
                    case 24: loanPetition.setAmount(loanPetition.getAmount() * 1.40);
                        break;
                    case 36: loanPetition.setAmount(loanPetition.getAmount() * 1.45);
                        break;
                    case 48: loanPetition.setAmount(loanPetition.getAmount() * 1.50);
                        break;
                    case 60: loanPetition.setAmount(loanPetition.getAmount() * 1.55);
                        break;
                }
                break;
            case "Automotriz":
                switch (loanPetition.getPayments()) {
                    case 6:
                        loanPetition.setAmount(loanPetition.getAmount() * 1.20);
                        break;
                    case 12:
                        loanPetition.setAmount(loanPetition.getAmount() * 1.30);
                        break;
                    case 24:
                        loanPetition.setAmount(loanPetition.getAmount() * 1.40);
                        break;
                    case 36:
                        loanPetition.setAmount(loanPetition.getAmount() * 1.45);
                        break;
                }
                break;
            default:
                break;

        }


        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/loans")
    public List<LoanDTO> getLoan (){
        return loanService.getAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }

    @PostMapping("/api/admin/loans")
    public ResponseEntity<Object> addLoanByAdmin(@RequestParam String name,@RequestParam Double maxAmount, @RequestParam List<Integer> payments , Authentication authentication) {

        Client adminAuthentication = clientService.getClientByEmail(authentication.getName());

        Loan loanAuthentication = loanService.getLoanByName(name);

        if (adminAuthentication == null) {

            return new ResponseEntity<>("Missing admin authentication", HttpStatus.FORBIDDEN);

        }
        if (name.isEmpty()) {

            return new ResponseEntity<>("Missing name of loan", HttpStatus.FORBIDDEN);

        }
        if (maxAmount <= 0 ) { //maxAmount.toString().isEmpty() me tira object object cdo paso el campo vacio

            return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);

        }
        if (payments.isEmpty()) {

            return new ResponseEntity<>("Missing payments", HttpStatus.FORBIDDEN);

        }
        if (loanService.getAllLoans().stream().map(x -> x.getName()).collect(toSet()).contains(name)) {

            return new ResponseEntity<>("Same name of previous loan", HttpStatus.FORBIDDEN);

        }

        loanService.saveLoan(new Loan(name, maxAmount, payments));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

