package org.example.gameapi.repository;

import org.example.gameapi.entity.Hero;
import org.example.gameapi.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
    List<Item> findByOwnerId(Integer id);
    List<Item> findByOwnerIsNull();

    List<Item> findByType(String type);
    List<Item> findByOwnerId(int heroId);
    List<Item> findByTypeAndPriceLessThanEqual(String type, int maxPrice);

    @Query("SELECT SUM(i.power) FROM Item i WHERE i.owner.id = ?1")
    Integer totalPowerOfHero(int heroId);
}
