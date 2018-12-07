package com.hangman.game.repository;

import com.hangman.game.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("wordRepository")
public interface WordRepository extends JpaRepository<Word, Integer> {

    Word findByWord(String role);

    @Query(value = "SELECT * FROM Word ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Word getRandomWord();

}
