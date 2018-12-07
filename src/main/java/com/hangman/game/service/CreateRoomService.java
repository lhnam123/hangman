package com.hangman.game.service;

import com.hangman.game.business.GameOutput;
import com.hangman.game.model.Room;
import com.hangman.game.model.User;
import com.hangman.game.model.Word;
import com.hangman.game.repository.RoomRepository;
import com.hangman.game.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("createRoomService")
public class CreateRoomService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameCacheService gameCacheService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WordRepository wordRepository;

    public GameOutput execute(final String roomName, final String userEmail) {

        if (roomName == null) {
            return new GameOutput("ok", null);
        }

        User user = userService.findUserByEmail(userEmail);

        Word word = wordRepository.getRandomWord();

        Room croom = roomRepository.findByName(roomName);

        if (croom != null) {
            return new GameOutput("ok", null);
        }

        Room room = roomRepository
                .save(Room.builder().name(roomName).creator(user.getId()).word(word).status("waiting").build());

        gameCacheService.setLastRoomId(room.getId());

        return new GameOutput("ok", room.getId());

    }

}