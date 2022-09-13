package com.mindhub.homebanking.services.Implementations;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplementation implements CardService {

    @Autowired
    public CardRepository cardRepository;

    @Override
    public void saveCard(Card card){
        cardRepository.save(card);
    }

    @Override
    public Card getCardByNumber (String number) {

        return cardRepository.findByNumber(number);

    }

}
