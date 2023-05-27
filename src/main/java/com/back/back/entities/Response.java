package com.back.back.entities;

import lombok.*;

@Data
@AllArgsConstructor
public class Response<ImageModel,Card> {

    private ImageModel image;
    private Card card;

}