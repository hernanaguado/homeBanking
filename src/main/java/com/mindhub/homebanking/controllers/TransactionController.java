package com.mindhub.homebanking.controllers;

import com.itextpdf.text.DocumentException;
import com.mindhub.homebanking.dtos.PdfDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.utils.PDFMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;


@RestController
public class TransactionController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;



    @Transactional // si hay algun error vuelve al estado inicial.
    @RequestMapping(path = "/api/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> newTransaction(@RequestParam Double amount, @RequestParam String description, @RequestParam String originAccount, @RequestParam String destinyAccount, Authentication authentication) {

        Client newClientAuthentication = clientService.getClientByEmail(authentication.getName());
        Account newOriginAccount = accountService.getAccountByNumber(originAccount);
        Account newDestinyAccount = accountService.getAccountByNumber(destinyAccount);

        if (newClientAuthentication == null) {
            return new ResponseEntity<>("The client doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (newOriginAccount == null) {
            return new ResponseEntity<>("Origin account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if (newDestinyAccount == null) {
            return new ResponseEntity<>("Destiny account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0) {
            return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);
        }
        if ( description.isEmpty()) {
            return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);
        }
       if (originAccount.isEmpty()) {
           return new ResponseEntity<>("Missing originAccount", HttpStatus.FORBIDDEN);
         }
         if (destinyAccount.isEmpty()) {
           return new ResponseEntity<>("Missing destinyAccount", HttpStatus.FORBIDDEN);
        }
        if (originAccount.equals(destinyAccount)) {
            return new ResponseEntity<>("Same account´s", HttpStatus.FORBIDDEN);
        }

        if(accountService.getAccountByNumber(originAccount).getBalance() < amount){
            return new ResponseEntity<>("Not enough money", HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(newOriginAccount,DEBIT,-amount,description + destinyAccount,LocalDateTime.now());
        Transaction creditTransaction = new Transaction(newDestinyAccount,CREDIT,amount,description + originAccount,LocalDateTime.now());
        transactionService.saveTransaction (debitTransaction);
        transactionService.saveTransaction (creditTransaction);

        newOriginAccount.setBalance(newOriginAccount.getBalance() - amount);
        newDestinyAccount.setBalance(newDestinyAccount.getBalance() + amount);

        accountService.saveAccount(newOriginAccount);
        accountService.saveAccount(newDestinyAccount);



        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @RequestMapping(path = "/api/PDF", method = RequestMethod.POST)
    public ResponseEntity<Object> PdfDownload(@RequestBody PdfDTO pdfDTO, Authentication authentication) throws DocumentException, FileNotFoundException {

        Account accountID = accountService.getAccountByNumber(pdfDTO.getAccount());
        Client clientAuthentication = clientService.getClientByEmail(authentication.getName());

        if (accountID == null) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }
        if(clientAuthentication == null) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }
        if (pdfDTO.getFromDate() == null) {

            return new ResponseEntity<>("Missing first date", HttpStatus.FORBIDDEN);

        }
        if (pdfDTO.getFinalDate() == null) {

            return new ResponseEntity<>("Missing second date", HttpStatus.FORBIDDEN);

        }

        Set<Transaction> transactionSearch = transactionService.getTransactionByDate(pdfDTO.getFromDate(),pdfDTO.getFinalDate(),accountID);

        PDFMethod.createPDF(transactionSearch);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }



}

