package com.back.back.entities;


import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {
    @Id
    private int id;
    @Column
    private String title;

    @Column
    private String description;
    @Column
    private String image;


}
