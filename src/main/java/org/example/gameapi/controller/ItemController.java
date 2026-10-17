package org.example.gameapi.controller;

import jakarta.validation.Valid;
import org.example.gameapi.entity.Item;
import org.example.gameapi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@CrossOrigin
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping("create")
    public Item createItem(@Valid @RequestBody Item item) {
        return itemService.create(item);
    }

    @GetMapping("shop")
    public List<Item> shop() {
        return itemService.shop();
    }

    @GetMapping("search")
    public List<Item> search(@RequestParam String type, @RequestParam int maxPrice) {
        return itemService.findAll(type, maxPrice);
    }

    @GetMapping("{id}")
    public Item getById(@PathVariable Integer id) {
        return itemService.findById(id);
    }
}
