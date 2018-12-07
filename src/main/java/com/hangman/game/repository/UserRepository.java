package com.hangman.game.repository;

import com.hangman.game.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Modifying
    @Query("update User u set u.lastUpdate = :time where u.email = :email")
    void updateHeartbeat(@Param("time") int time, @Param("email") String email);

    @Modifying
    @Query("update User u set u.win = u.win + 1 where u.id = :userId")
    void increaseWin(@Param("userId") int userId);

    @Modifying
    @Query("update User u set u.lose = u.lose + 1 where u.id = :userId")
    void increaseLose(@Param("userId") int userId);
}