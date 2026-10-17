package org.example.gameapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.gameapi.entity.Hero;
import org.example.gameapi.entity.Item;
import org.example.gameapi.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hero")
@CrossOrigin
@Tag(name="hero" ,description = "управление героями/персонажами")
public class HeroController {

    @Autowired
    private HeroService heroService;

    @GetMapping
    @Operation(summary = "получить всех героев")
    public List<Hero> getAll() {
        return heroService.getHeroes();
    }




//      hero/4
    @GetMapping("/{id}")
    public Hero getById(@Parameter(description = "ID персонажа") @PathVariable int id) {
        return heroService.getHeroById(id);
    }

    @PostMapping("/create")
    public Hero create(@Valid @RequestBody Hero hero) {
        return heroService.createHero(hero);
    }

    @GetMapping("/{heroId}/items")
    public List<Item> items(@PathVariable int heroId) {
        return heroService.getItems(heroId);
    }

    @GetMapping("/{heroId}/power")
    public Integer power(@PathVariable int heroId) {
        return heroService.powerOfHero(heroId);
    }

    @PostMapping("/{heroId}/buy/{itemId}")
    public Hero buy(@PathVariable int heroId, @PathVariable int itemId) {
        return heroService.buy(heroId, itemId);
    }

    @PostMapping("/{heroId}/sell/{itemId}")
    public Hero sell(@PathVariable int heroId, @PathVariable int itemId) {
        return heroService.sell(heroId, itemId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        heroService.deleteHero(id);
    }
}
