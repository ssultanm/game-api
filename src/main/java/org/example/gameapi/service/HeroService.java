package org.example.gameapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.gameapi.entity.Hero;
import org.example.gameapi.entity.Item;
import org.example.gameapi.exception.HeroNotFoundException;
import org.example.gameapi.repository.HeroRepository;
import org.example.gameapi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class HeroService {
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Value("${game.max-items}")
    private int maxItems;

    @Value("${game.start-gold}")
    private int startGold;

    public List<Hero> getHeroes() {
        log.info("метод получить всех героев вызван");
        List<Hero> heroes = heroRepository.findAll();
        log.info("найдено героев: {}", heroes.size());
        return heroes;
    }


    public Hero getHeroById(int id) {
        log.info("метод получить по id вызван ");
  if (id==0){
      log.warn("user id is 0");
  }
        return heroRepository.findById(id).orElseThrow(()->new HeroNotFoundException(id));
    }
    public Hero createHero(Hero hero) {
        log.info("метод создать героя вызван, имя={}", hero.getName());
        hero.setGold(startGold);
        Hero saved = heroRepository.save(hero);
        log.info("герой создан, id={}, стартовое золото={}", saved.getId(), startGold);
        return saved;
    }
    @Transactional
    public void deleteHero(int id) {
        log.info("метод удалить героя вызван, id={}", id);
        Hero hero = getHeroById(id);
        List<Item> backpack = itemRepository.findByOwnerId(hero.getId());
        log.info("у героя id={} в рюкзаке {} предметов, освобождаем их", id, backpack.size());
        for (Item item : backpack) {
            item.setOwner(null);
        }
        itemRepository.saveAll(backpack);

        heroRepository.delete(hero);
        log.info("герой id={} удалён", id);
    }

    public Hero buy(int heroId, int itemId) {
        log.info("метод покупки вызван, heroId={}, itemId={}", heroId, itemId);
        Hero hero = getHeroById(heroId);
        Item item = itemRepository.findById(itemId).get();

        if (item.getOwner().getId() == hero.getId()) {
            log.warn("герой id={} уже владеет предметом id={}", heroId, itemId);
            throw new RuntimeException("Hero with id " + heroId + " already owns ");
        }
        int inBackpack = itemRepository.findByOwnerId(hero.getId()).size();
        if (inBackpack >= maxItems) {
            log.warn("рюкзак героя id={} полон ({}/{})", heroId, inBackpack, maxItems);
            throw new RuntimeException("Hero with id " + heroId + " is full");
        }
        if (hero.getGold() < item.getPrice()) {
            log.warn("у героя id={} не хватает золота: есть {}, нужно {}", heroId, hero.getGold(), item.getPrice());
            throw new RuntimeException("Hero with id " + heroId + " is below the gold");
        }

        hero.setGold(hero.getGold() - item.getPrice());
        item.setOwner(hero);
        itemRepository.save(item);
        Hero saved = heroRepository.save(hero);
        log.info("герой id={} купил предмет id={} за {}, осталось золота {}", heroId, itemId, item.getPrice(), saved.getGold());
        return saved;
    }
    public Hero sell(int heroId, int itemId) {
        log.info("метод продажи вызван, heroId={}, itemId={}", heroId, itemId);
        Hero hero = getHeroById(heroId);
        Item item = itemRepository.findById(itemId).get();

        if (item.getOwner().getId() != hero.getId()) {
            log.warn("предмет id={} не принадлежит герою id={}", itemId, heroId);
            throw new RuntimeException("it is not hero`s item with id " + heroId);
        }

        int income = item.getPrice() / 2;
        hero.setGold(hero.getGold() + income);
        item.setOwner(null);
        itemRepository.save(item);
        Hero saved = heroRepository.save(hero);
        log.info("герой id={} продал предмет id={} за {}, теперь золота {}", heroId, itemId, income, saved.getGold());
        return saved;
    }
//сила = level * 10 + сумма power всех предметов в рюкзаке
public Integer powerOfHero(int heroId) {
    log.info("метод расчёта силы вызван, heroId={}", heroId);
    Hero hero = getHeroById(heroId);
    int power = hero.getLevel() * 10 + itemRepository.totalPowerOfHero(heroId);
    if(power==0){
        return 0;
    }
    log.info("сила героя id={} = {}", heroId, power);
    return power;
}
    public List<Item> getItems(int heroId) {
        log.info("метод получить предметы героя вызван, heroId={}", heroId);
        Hero hero = getHeroById(heroId);
        List<Item> inBackpack = itemRepository.findByOwnerId(hero.getId());
        log.info("у героя id={} найдено {} предметов", heroId, inBackpack.size());
        return inBackpack;
    }
}
