package com.hangman.game.controller;

import com.hangman.game.model.Room;
import com.hangman.game.model.User;
import com.hangman.game.repository.RoomRepository;
import com.hangman.game.repository.UserRepository;
import com.hangman.game.service.JoinGameService;
import com.hangman.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private UserService userService;

    @Autowired
    private JoinGameService joinGameService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("wellcome", "Hello " + user.getName());
        modelAndView.addObject("userName", user.getName());
        modelAndView.setViewName("game/home");
        return modelAndView;
    }

    @RequestMapping(value = "/achievement", method = RequestMethod.GET)
    public ModelAndView achievement() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("wellcome", "Hello " + user.getName());
        modelAndView.addObject("userName", user.getName());
        modelAndView.addObject("win", user.getWin());
        modelAndView.addObject("lose", user.getLose());

        modelAndView.setViewName("game/achievement");
        return modelAndView;
    }

    @RequestMapping(value = "/play/{roomId}", method = RequestMethod.GET)
    public ModelAndView play(@PathVariable final int roomId) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Room r = roomRepository.findById(roomId).get();

        modelAndView.addObject("wellcome", "Hello " + user.getName());
        modelAndView.addObject("roomId", roomId);
        modelAndView.addObject("roomName", r.getName());
        modelAndView.addObject("wordDesc", r.getWord().getDescription());
        modelAndView.addObject("wordLenght", r.getWord().getWord().length());
        modelAndView.addObject("userId", user.getId());
        modelAndView.addObject("userName", user.getName());
        String playerName = "";
        if (r.getEnemyId(user.getId()) != null) {
            playerName = userRepository.findById(r.getEnemyId(user.getId())).get().getName();
        }

        modelAndView.addObject("playerName", playerName);

        modelAndView.setViewName("game/play");

        if (!joinGameService.execute(roomId, user)) {
            modelAndView.setViewName("game/home");
        }
        return modelAndView;
    }

}
