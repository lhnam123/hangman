package com.hangman.game.service;

import com.hangman.game.model.GameCache;
import com.hangman.game.repository.GameCacheRepository;
import com.hangman.game.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameCacheService")
public class GameCacheService {

    @Autowired
    private GameCacheRepository gameCacheRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void push(final String key, final String value) {
        GameCache gameCache = gameCacheRepository.findFirstByKey(key);
        if (gameCache != null) {
            gameCache.setValue(value);
            gameCacheRepository.save(gameCache);
        } else {
            gameCacheRepository.save(GameCache.builder().key(key).value(value).build());
        }
    }

    public String get(final String key) {
        GameCache gameCache = gameCacheRepository.findFirstByKey(key);
        if (gameCache != null) {
            return gameCache.getValue();
        }
        return "-1";
    }

    public void setLastRoomId(final int roomId) {
        push("last_room", roomId + "");
    }

    public int getLastRoomId() {
        int lastRoomId = Integer.parseInt(get("last_room"));
        if (lastRoomId != -1) {
            return lastRoomId;
        }

        Integer lastId = roomRepository.findLastRoomId();

        if (lastId == null) {
            return -1;
        }

        push("last_room", lastId + "");

        return lastId;
    }

    public String getRoomStatus(final int roomId) {
        return get("room_status_" + roomId);
    }

    public void setRoomStatus(final int roomId, final String status) {
        push("room_status_" + roomId, status);
    }

    public int getFailStep(final int roomId, final int userId) {
        return Integer.parseInt(get("fail_step_" + roomId + "_" + userId));
    }

    public void setFailStep(final int roomId, final int userId, final int step) {
        push("fail_step_" + roomId + "_" + userId, step + "");
    }

    public void addOneFailStep(final int roomId, final int userId) {
        setFailStep(roomId, userId, getFailStep(roomId, userId) + 1);
    }

    public void setResult(final int roomId, final int userId, final String status) {
        push("result_" + roomId + "_" + userId, status);
    }

    public String getResult(final int roomId, final int userId) {
        return get("result_" + roomId + "_" + userId);
    }
}
