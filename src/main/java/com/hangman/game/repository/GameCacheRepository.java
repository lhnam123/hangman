package com.hangman.game.repository;

import com.hangman.game.model.GameCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("employeeRepository")
public interface GameCacheRepository extends CrudRepository<GameCache, Integer> {
    //
    GameCache findFirstByKey(String key);
}