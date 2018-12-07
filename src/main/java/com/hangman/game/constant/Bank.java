package com.hangman.game.constant;

public class Bank {

    public static final long PULLING_TIME_LIVE = 90000;// pulling 90s

    public static final long PULLING_STEP = 100;

    public static final int MAX_STEP = (int) (PULLING_TIME_LIVE / PULLING_STEP);

}
