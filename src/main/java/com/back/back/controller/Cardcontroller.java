package com.back.back.controller;


import com.back.back.entities.Card;
import com.back.back.exceptions.ResourceNotFoundException;
import com.back.back.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class Cardcontroller {

    @Autowired
    private CardRepository cardRepository;

    //Get all cards


    @GetMapping("/cards")
    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }


    //Get card by id

    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable Integer id){
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not exist with id :" + id));
        return ResponseEntity.ok(card);
    }

    // Create card

    @PostMapping("/card")
    public Card createCard(@RequestBody Card card){
        return cardRepository.save(card);
    }

    // Update card

    @PostMapping("/cards/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable Integer id, @RequestBody Card updatesCrad) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not exist with id :" + id));

        card.setTitle(updatesCrad.getTitle());
        card.setDescription(updatesCrad.getDescription());
        card.setImage(updatesCrad.getDescription());
        Card updated =cardRepository.save(card);
        return ResponseEntity.ok(updated);

    }

    // Delete card

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteCard(@PathVariable Integer id){
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not exist with id :" + id));
        cardRepository.delete(card);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
