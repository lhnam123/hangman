package com.hangman.game.service;

import com.hangman.game.business.GameOutput;
import com.hangman.game.business.GameStatus;
import com.hangman.game.constant.Bank;
import com.hangman.game.model.Room;
import com.hangman.game.model.User;
import com.hangman.game.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roomSenceService")
public class RoomSenceService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameCacheService gameCacheService;

    @Autowired
    private RoomRepository roomRepository;

    public GameOutput execute(final int roomId, final String status, final String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        Room r = roomRepository.findById(roomId).get();

        int counter = Bank.MAX_STEP;
        while (true) {
            String roomStatus = gameCacheService.getRoomStatus(roomId);
            if (roomStatus.equals("-1")) {
                roomStatus = r.getStatus();
                gameCacheService.setRoomStatus(roomId, roomStatus);
            }

            switch (roomStatus) {
                case "waiting":
                    // do nothing
                    break;
                case "ready":
                    if (user.getId() == r.getCreator() && !roomStatus.equals(status)) {
                        return new GameOutput("ok", GameStatus.builder().gameStatus("ready").build());
                    }
                    break;
                case "started":
                    if (!roomStatus.equals(status)) {
                        return new GameOutput("ok", GameStatus.builder().gameStatus("started").build());
                    }
                    break;
                case "finished":
                    if (!roomStatus.equals(status)) {
                        return new GameOutput("ok", GameStatus.builder().gameStatus("finished").build());
                    }
                    break;
                default:
                    break;
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