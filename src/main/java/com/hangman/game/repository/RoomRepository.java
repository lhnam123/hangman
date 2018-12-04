package com.hangman.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hangman.game.model.Room;

@Repository("roomRepository")
public interface RoomRepository extends JpaRepository<Room, Integer> {

	Room findByPlayerAndActivatedIsTrue(int player);

	Room findByCreatorAndActivatedIsTrue(int creator);

	Room findByName(String name);
}
