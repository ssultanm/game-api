package org.example.gameapi.service;

import org.example.gameapi.entity.Item;
import org.example.gameapi.repository.HeroRepository;
import org.example.gameapi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private HeroRepository heroRepository;

    public List<Item> findAll(String type, int maxPrice) {
        return     itemRepository.findByTypeAndPriceLessThanEqual(type, maxPrice);
    }
public List<Item> shop(){
    return itemRepository.findByOwnerIsNull();

}
 public Item findById(Integer id){
        return itemRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
 }
public Item create(Item item){
        item.setOwner(null);
        return  itemRepository.save(item);
}
}
