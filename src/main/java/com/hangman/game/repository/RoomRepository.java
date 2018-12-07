package com.hangman.game.repository;

import com.hangman.game.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("roomRepository")
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room findByPlayer(Integer player);

    Room findByCreator(Integer creator);

    Room findByName(String name);

    @Query(value = "SELECT max(id) From Room")
    Integer findLastRoomId();

    @Query(value = "SELECT status From Room where id = :roomId")
    String getStatusByRoomId(@Param("roomId") int roomId);
}
