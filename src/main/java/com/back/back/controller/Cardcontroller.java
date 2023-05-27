package com.back.back.controller;


import com.back.back.entities.Card;
import com.back.back.entities.ImageModel;
import com.back.back.entities.Payload;
import com.back.back.entities.Response;
import com.back.back.exceptions.ResourceNotFoundException;
import com.back.back.repositories.CardRepository;
import com.back.back.repositories.ImageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


@CrossOrigin(origins = "*")
@RestController
public class Cardcontroller {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ImageRepository imageRepository;


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




    @PostMapping(value ="/cards",  consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createCard(@RequestParam("data") String cardData, @RequestParam("file") MultipartFile file) throws IOException {

        System.out.println("Card Data: " + cardData);

        //String to Card object
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Card card = objectMapper.readValue(cardData, Card.class);


            System.out.println("Id: " + card.getId());
            System.out.println("Title: " + card.getTitle());
            System.out.println("Description: " + card.getDescription());

            cardRepository.save(card);
        } catch (Exception e) {
            e.printStackTrace();
        }



        if (file != null) {
            // Handle the
            System.out.println("File Name: " + file.getOriginalFilename());

            ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
                    compressBytes(file.getBytes()));
            imageRepository.save(img);
        }


        return ResponseEntity.ok("Card created successfully");
    }


    // Update card

    @PostMapping("/cards/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable Integer id, @RequestBody Card updateCrad) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not exist with id :" + id));

        card.setTitle(updateCrad.getTitle());
        card.setDescription(updateCrad.getDescription());
     /*   card.setImage(updateCrad.getImage());*/
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




    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}


