package com.back.back.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

@Data
@AllArgsConstructor
public class Payload {
    private Json card;
    private MultipartFile file;
}
