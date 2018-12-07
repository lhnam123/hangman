package com.hangman.game.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameStep {

    private String name;

    private String creator;

    private String player;

    private String status;
}
