package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;



@RestController
public class CardController {


    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;



    @RequestMapping(path = "/api/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> newCards(@RequestParam String cardColor, @RequestParam String cardType, Authentication authentication) {

        Client authenticationClient = clientService.getClientByEmail(authentication.getName());
        String randomCardNumber = CardUtils.getRandomNumberCard();
        Integer randomCvvNumber = CardUtils.getCvvNumber();

        if ( cardType.isEmpty() )  {



            return new ResponseEntity<>("Missing cardType", HttpStatus.FORBIDDEN);

        }
        if ( cardColor.isEmpty() )  {

            return new ResponseEntity<>("Missing cardColor", HttpStatus.FORBIDDEN);

        }
        if (authenticationClient.getCards().stream().filter(card -> card.getCardType().toString().equals(cardType) && card.isStateOfCards()).count() >= 3 ) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        if(authenticationClient.getCards().stream().anyMatch(card -> card.getCardColor().toString().equals(cardColor) && card.getCardType().toString().equals(cardType))){

            return new ResponseEntity<>("for each card you can only select one color", HttpStatus.FORBIDDEN);

        }

        cardService.saveCard(new Card(authenticationClient, authenticationClient.toString(), randomCardNumber, randomCvvNumber,LocalDateTime.now(),LocalDateTime.now().plusYears(5),CardColor.valueOf(cardColor),CardType.valueOf(cardType),true));
        return new ResponseEntity<>(HttpStatus.CREATED);

}

    @RequestMapping(path = "/api/clients/current/cards/state", method = RequestMethod.PATCH)
    public ResponseEntity<Object> changeStateOfCardsDelete(@RequestParam String cardNumber,Authentication authentication) {

        Client authenticationClient = clientService.getClientByEmail(authentication.getName());
        Card cardNumberClient = cardService.getCardByNumber(cardNumber);


        if (authenticationClient == null) {

            return new ResponseEntity<>("Missing client", HttpStatus.FORBIDDEN);

        }
        if (cardNumberClient == null) {

            return new ResponseEntity<>("Missing client", HttpStatus.FORBIDDEN);

        }
        if (cardNumber.isEmpty()) {

            return new ResponseEntity<>("Missing number card", HttpStatus.FORBIDDEN);
        }
        if (!authenticationClient.getCards().contains(cardNumberClient)) {

            return new ResponseEntity<>("Missing number card", HttpStatus.FORBIDDEN);

        }



        cardNumberClient.setStateOfCards(false);
        cardService.saveCard(cardNumberClient);
        return new ResponseEntity<>(HttpStatus.CREATED);


    }

}