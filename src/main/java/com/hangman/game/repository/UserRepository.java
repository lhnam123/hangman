package com.hangman.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hangman.game.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	@Modifying
	@Query("update User u set u.lastUpdate = :time where u.email = :email")
	void updateHeartbeat(@Param("time") int time, @Param("email") String email);
}