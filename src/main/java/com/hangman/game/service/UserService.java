package com.hangman.game.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hangman.game.model.Role;
import com.hangman.game.model.User;
import com.hangman.game.repository.RoleRepository;
import com.hangman.game.repository.UserRepository;

@Service("userService")
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserService(final UserRepository userRepository, final RoleRepository roleRepository,
			final BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	public User saveUser(final User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByRole("USER");
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		return userRepository.save(user);
	}

	@Transactional
	public void updateHeartbeat(final int now, final String email) {
		userRepository.updateHeartbeat(now, email);
	}

}