package org.example.gameapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.example.gameapi.validation.HeroClass;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message="имя не может быть пустым")
    private String name;
@HeroClass
    private String heroClass; // WARRIOR / MAGE / ARCHER
   @Positive(message = "level не может быть отрицательный")
    private Integer level;

    @Positive
    private Integer gold;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Item> inventory = new ArrayList<>();
}
