package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
public class AcountsController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;


    @GetMapping ("/api/account") //micro servicio serv let
    public List<AccountDTO> getAccounts(){
        return accountService.getAllAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    //serv let
    @GetMapping("/api/account/{id}")
    public AccountDTO getAccount(@PathVariable long id) {
        return new AccountDTO(accountService.getAccountById(id));
    }

    @RequestMapping (path = "/api/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(@RequestParam String accountType,Authentication authentication){

        String randomNumber = "VIN-" + getRandomNumber(0,99999999);

        Client newCurrentClient = clientService.getClientByEmail(authentication.getName());

        if(accountType.isEmpty()){

            return new ResponseEntity<>("Missing account type", HttpStatus.FORBIDDEN);

        }

        if (newCurrentClient.getAccounts().stream().filter(account -> account.isAccountState() == true).count()>=3 ) {

            return new ResponseEntity<>("Just 3 accounts", HttpStatus.FORBIDDEN);

        } else  {

            accountService.saveAccount(new Account(randomNumber,.0, LocalDateTime.now(),newCurrentClient,true, AccountType.valueOf(accountType)));
            return new ResponseEntity<>(HttpStatus.CREATED);

        }

    }


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @RequestMapping (path = "/api/clients/current/account/state", method = RequestMethod.PATCH)
        public ResponseEntity<Object> changeStateOfAccountDelete(@RequestParam String accountNumber,Authentication authentication){

        Client authenticationClient = clientService.getClientByEmail(authentication.getName());
        Account accountNumberClient = accountService.getAccountByNumber(accountNumber);

        if (authenticationClient == null) {

            return new ResponseEntity<>("Missing client", HttpStatus.FORBIDDEN);

        }
        if (!authenticationClient.getAccounts().contains(accountNumberClient)) {

            return new ResponseEntity<>("Missing number card", HttpStatus.FORBIDDEN);

        }
        if (accountNumber.isEmpty()) {

            return new ResponseEntity<>("Missing number card", HttpStatus.FORBIDDEN);
        }
        if (accountNumberClient.getBalance() > 0 ){

            return new ResponseEntity<>("You have money in the current account", HttpStatus.FORBIDDEN);

        }

        accountNumberClient.setAccountState(false);
        accountService.saveAccount(accountNumberClient);
        return new ResponseEntity<>(HttpStatus.CREATED);

        }

}