package org.example.gameapi.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
@NotBlank
    private String title;

    private String type; // WEAPON / ARMOR / POTION
@NotBlank
@Positive
    private Integer price;
    @NotBlank
    @Positive
    private Integer power; // сколько силы добавляет герою

    @ManyToOne
    @JoinColumn(name = "hero_id")
    private Hero owner;
}