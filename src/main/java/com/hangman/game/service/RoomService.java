package com.hangman.game.service;

import com.hangman.game.business.RoomInfo;
import com.hangman.game.model.Room;
import com.hangman.game.model.User;
import com.hangman.game.repository.RoomRepository;
import com.hangman.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("roomService")
public class RoomService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    public RoomInfo getRomInfo(final int roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()) {
            Room r = room.get();
            Optional<User> creator = userRepository.findById(r.getCreator());
            String player = "";
            if (r.getPlayer() != null) {
                player = userRepository.findById(r.getPlayer()).get().getName();
            }

            return RoomInfo.builder().creator(creator.get().getName()).status(r.getStatus()).player(player).id(roomId)
                    .name(room.get().getName()).build();
        }
        return null;
    }

}