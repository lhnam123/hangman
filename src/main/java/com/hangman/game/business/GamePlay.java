package com.hangman.game.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GamePlay {

    private String gameStatus;

    private int myFail;

    private int enemyFail;

    private String status;

}
