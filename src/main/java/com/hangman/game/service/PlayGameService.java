package com.hangman.game.service;

import com.hangman.game.business.GameOutput;
import com.hangman.game.model.Room;
import com.hangman.game.model.User;
import com.hangman.game.repository.RoomRepository;
import com.hangman.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("playGameService")
public class PlayGameService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameCacheService gameCacheService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public GameOutput execute(final int roomId, final String userEmail, final String word) {
        User user = userService.findUserByEmail(userEmail);
        int userId = user.getId();
        Optional<Room> room = roomRepository.findById(roomId);
        if (!room.isPresent()) {
            return new GameOutput("error", "room not exist");
        }

        Room r = room.get();
        if (r.getCreator() != userId && r.getPlayer() != userId) {
            return new GameOutput("error", "Your don't join this room");
        }

        List<Integer> result = new ArrayList<>();

        int index = 0;
        for (String w : r.getWord().getWord().toLowerCase().split("")) {
            if (w.equals(word)) {
                result.add(index);
            }
            index++;
        }
        int wordLenght = r.getWord().getWord().length();

        if (r.getCreator() == userId) {
            if (result.isEmpty()) {
                int failStep = r.getStepFailCreator();
                failStep++;
                if (failStep >= 10) {
                    gameCacheService.setResult(roomId, r.getCreator(), "lose");
                    gameCacheService.setResult(roomId, r.getPlayer(), "win");
                    r.setWinner(r.getPlayer());
                    userRepository.increaseWin(r.getPlayer());
                    userRepository.increaseLose(r.getCreator());
                    r.setStatus("finished");
                }
                r.setStepFailCreator(failStep);
                gameCacheService.setFailStep(roomId, userId, failStep);

            } else {
                int correctStep = r.getStepTrueCreator();
                correctStep += result.size();
                if (correctStep >= wordLenght) {
                    gameCacheService.setResult(roomId, r.getCreator(), "win");
                    gameCacheService.setResult(roomId, r.getPlayer(), "lose");
                    r.setWinner(r.getCreator());
                    userRepository.increaseWin(r.getCreator());
                    userRepository.increaseLose(r.getPlayer());
                    r.setStatus("finished");
                }
                r.setStepTrueCreator(correctStep);
                roomRepository.save(r);
                return new GameOutput("ok", result);
            }

        } else {
            if (result.isEmpty()) {
                int failStep = r.getStepFailPlayer();
                failStep++;
                if (failStep >= 10) {
                    gameCacheService.setResult(roomId, r.getCreator(), "win");
                    gameCacheService.setResult(roomId, r.getPlayer(), "lose");
                    r.setWinner(r.getCreator());
                    userRepository.increaseWin(r.getCreator());
                    userRepository.increaseLose(r.getPlayer());
                    r.setStatus("finished");
                }
                r.setStepFailPlayer(failStep);
                gameCacheService.setFailStep(roomId, userId, failStep);

            } else {
                int correctStep = r.getStepTruePlayer();
                correctStep += result.size();
                if (correctStep >= wordLenght) {
                    gameCacheService.setResult(roomId, r.getCreator(), "lose");
                    gameCacheService.setResult(roomId, r.getPlayer(), "win");
                    r.setWinner(r.getPlayer());
                    userRepository.increaseWin(r.getCreator());
                    userRepository.increaseLose(r.getPlayer());
                    r.setStatus("finished");
                }
                r.setStepTruePlayer(correctStep);
                roomRepository.save(r);
                return new GameOutput("ok", result);
            }

        }
        roomRepository.save(r);
        return new GameOutput("ok", null);

    }

}