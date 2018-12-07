package com.hangman.game.service;

import com.hangman.game.model.Room;
import com.hangman.game.model.User;
import com.hangman.game.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("joinGameService")
public class JoinGameService {

    @Autowired
    private GameCacheService gameCacheService;

    @Autowired
    private RoomRepository roomRepository;

    public boolean execute(final int roomId, final User user) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (!room.isPresent()) {
            return false;
        }

        Room r = room.get();
        if (r.getCreator() == user.getId()) {
            return true;
        }

        if (r.getPlayer() != null) {
            if (r.getPlayer() != user.getId()) {
                return false;
            }
            return true;
        }

        String roomStatus = gameCacheService.getRoomStatus(roomId);
        if (roomStatus.equals("-1")) {
            roomStatus = r.getStatus();
            gameCacheService.setRoomStatus(roomId, roomStatus);
        }
        if (!roomStatus.equals("waiting")) {
            return false;
        }

        r.setPlayer(user.getId());
        r.setStatus("ready");
        roomRepository.save(r);
        gameCacheService.setRoomStatus(roomId, "ready");

        return true;
    }

}