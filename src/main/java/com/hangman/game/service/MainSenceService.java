package com.hangman.game.service;

import com.hangman.game.business.GameOutput;
import com.hangman.game.business.RoomInfo;
import com.hangman.game.constant.Bank;
import com.hangman.game.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("mainSenceService")
public class MainSenceService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameCacheService gameCacheService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    public GameOutput execute(final int roomId, final String userEmail) {
        // User user = userService.findUserByEmail(userEmail);

        int counter = Bank.MAX_STEP;
        while (true) {
            int lastRoomId = getLastRoomId();

            if (lastRoomId > roomId) {
                List<RoomInfo> rooms = new ArrayList<>();
                for (int i = roomId; i < lastRoomId; i++) {
                    RoomInfo roomInfo = roomService.getRomInfo(i + 1);
                    if (roomInfo != null) {
                        rooms.add(roomInfo);
                    }
                }
                return new GameOutput("ok", rooms);
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

    private int getLastRoomId() {
        int lastRoomId = Integer.parseInt(gameCacheService.get("last_room"));
        if (lastRoomId < 0) {
            Integer lastId = roomRepository.findLastRoomId();
            if (lastId == null) {
                lastId = 0;
            }
            lastRoomId = lastId;
            gameCacheService.push("last_room", lastRoomId + "");
        }

        return lastRoomId;
    }

}