package org.example.gameapi.repository;

import org.example.gameapi.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<Hero,Integer> {
    List<Hero> findByHeroClass(String heroClass);
    List<Hero> findByGoldGreaterThanOrderByGoldDesc(int gold);

}
