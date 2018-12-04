package com.hangman.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangman.game.model.User;
import com.hangman.game.service.UserService;

@Controller
@RequestMapping("/game")
public class GameController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		int now = (int) (System.currentTimeMillis() / 1000L);

		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("wellcome", "Hello " + user.getName());

		modelAndView.setViewName("game/home");
		return modelAndView;
	}

	@RequestMapping(value = "/achievement", method = RequestMethod.GET)
	public ModelAndView achievement() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("wellcome", "Hello " + user.getName());
		modelAndView.addObject("win", user.getWin());
		modelAndView.addObject("drawn", user.getDrawn());
		modelAndView.addObject("lose", user.getLose());

		modelAndView.setViewName("game/achievement");
		return modelAndView;
	}

	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public ModelAndView play() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("wellcome", "Hello " + user.getName());

		modelAndView.setViewName("game/play");
		return modelAndView;
	}

}
