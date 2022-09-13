package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/api/clients") //peticion asociada a una ruta definida.
    public List<ClientDTO> getClients(){
        return  clientService.getAllClients().stream().map(client -> new ClientDTO(client)).collect(toList());
    }

    @GetMapping ("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){

        return new ClientDTO(clientService.getClientById(id));

    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/api/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email,@RequestParam String password) {

        if (firstName.isEmpty() ) {

            return new ResponseEntity<>("Missing first name", HttpStatus.FORBIDDEN);

        }

        if (lastName.isEmpty()) {

            return new ResponseEntity<>("Missing last name", HttpStatus.FORBIDDEN);

        }

        if (email.isEmpty()) {

            return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);

        }

        if (password.isEmpty()) {

            return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);

        }

        if (clientService.getClientByEmail(email) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }

        Client registerClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        String randomNumber = "VIN-" + getRandomNumber(0,99999999);
        String randomNumber2 = "VIN-" + getRandomNumber(0,99999999);
        clientService.saveClient(registerClient);
        accountService.saveAccount(new Account(randomNumber,.0, LocalDateTime.now(),registerClient,true, AccountType.CURRENT));
        accountService.saveAccount(new Account(randomNumber2,.0, LocalDateTime.now(),registerClient,true, AccountType.SAVING));
        return new ResponseEntity<>(HttpStatus.CREATED);

        }

        @GetMapping("/api/clients/current")
        public ClientDTO getCurrentClient(Authentication authentication) {

            return new ClientDTO(clientService.getClientByEmail(authentication.getName()));
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}
