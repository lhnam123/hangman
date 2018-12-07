package com.hangman.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@KeySpace("gameCache")
public class GameCache {

    @Id
    private Integer id;

    private String key;

    private String value;

}