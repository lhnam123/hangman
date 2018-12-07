package com.hangman.game.service;

import com.hangman.game.business.GameOutput;
import com.hangman.game.model.Room;
import com.hangman.game.model.User;
import com.hangman.game.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("startGameService")
public class StartGameService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameCacheService gameCacheService;

    @Autowired
    private RoomRepository roomRepository;

    public GameOutput execute(final int roomId, final String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        Optional<Room> room = roomRepository.findById(roomId);
        if (!room.isPresent()) {
            return new GameOutput("error", "room not exist");
        }

        Room r = room.get();
        if (r.getCreator() != user.getId()) {
            return new GameOutput("error", "only creator can start game");
        }

        r.setStatus("started");
        roomRepository.save(r);

        gameCacheService.setRoomStatus(roomId, "started");

        return new GameOutput("ok", null);
    }

}