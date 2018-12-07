package com.hangman.game.service;

import com.hangman.game.business.GameOutput;
import com.hangman.game.business.GamePlay;
import com.hangman.game.constant.Bank;
import com.hangman.game.model.Room;
import com.hangman.game.model.User;
import com.hangman.game.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("playSenceService")
public class PlaySenceService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameCacheService gameCacheService;

    @Autowired
    private RoomRepository roomRepository;

    public GameOutput execute(final int roomId, final String userEmail, final int myFail, final int ennemyFail) {
        User user = userService.findUserByEmail(userEmail);
        Room r = roomRepository.findById(roomId).get();
        int counter = Bank.MAX_STEP;
        while (true) {
            int myFailCache = gameCacheService.getFailStep(roomId, user.getId());
            if (myFailCache == -1) {
                myFailCache = user.getId() == r.getCreator() ? r.getStepFailCreator() : r.getStepFailPlayer();
                gameCacheService.setFailStep(roomId, user.getId(), myFailCache);
            }
            int ennemyFailCache = gameCacheService.getFailStep(roomId, r.getEnemyId(user.getId()));
            if (ennemyFailCache == -1) {
                ennemyFailCache = user.getId() == r.getPlayer() ? r.getStepFailCreator() : r.getStepFailPlayer();
                gameCacheService.setFailStep(roomId, r.getEnemyId(user.getId()), ennemyFailCache);
            }

            String result = gameCacheService.getResult(roomId, user.getId());
            if (r.getWinner() != null && result.equals("-1")) {
                result = user.getId() == r.getWinner() ? "win" : "lose";
                gameCacheService.setResult(roomId, user.getId(), result);
            }

            if (result.equals("win")) {
                return new GameOutput("ok",
                        GamePlay.builder().enemyFail(ennemyFailCache).myFail(myFailCache).status("win").build());
            }

            if (result.equals("lose")) {
                return new GameOutput("ok",
                        GamePlay.builder().enemyFail(ennemyFailCache).myFail(myFailCache).status("lose").build());
            }

            if (myFail != myFailCache || ennemyFailCache != ennemyFail) {
                return new GameOutput("ok", GamePlay.builder().enemyFail(ennemyFailCache).myFail(myFailCache)
                        .gameStatus("game_started").build());
            }

            try {
                Thread.sleep(Bank.PULLING_STEP);
            } catch (InterruptedException e) {
                return new GameOutput("error", null);
            }

            counter--;
            if (counter == 0) {
                return new GameOutput("ok", null);
            }

        }
    }

}