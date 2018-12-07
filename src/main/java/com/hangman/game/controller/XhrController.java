package com.hangman.game.controller;

import com.hangman.game.business.GameOutput;
import com.hangman.game.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;

@RestController
@RequestMapping("/xhr")
public class XhrController {

    @Autowired
    private UserService userService;

    @Autowired
    private MainSenceService mainSenceService;

    @Autowired
    private RoomSenceService roomSenceService;

    @Autowired
    private PlaySenceService playSenceService;

    @Autowired
    private StartGameService startGameService;

    @Autowired
    private PlayGameService playGameService;

    @Autowired
    private CreateRoomService createRoomService;

    @GetMapping("/heartbeat")
    public int heartbeat() {
        int now = (int) (System.currentTimeMillis() / 1000L);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.updateHeartbeat(now, auth.getName());
        return now;
    }

    @GetMapping("/room/{roomId}/_start")
    public GameOutput startGame(@PathVariable final int roomId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return startGameService.execute(roomId, userEmail);
    }

    @GetMapping("/room/{roomId}/word/{word}")
    public GameOutput submit(@PathVariable final int roomId, @PathVariable final String word) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return playGameService.execute(roomId, userEmail, word.toLowerCase());
    }

    @PostMapping("/_create")
    public GameOutput createRoom(@RequestParam final Map<String, String> body) {
        return createRoomService.execute(body.get("name"),
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/main/{roomId}")
    public DeferredResult<GameOutput> mainSence(@PathVariable final int roomId) {
        DeferredResult<GameOutput> deferredResult = new DeferredResult<>();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(() -> {
            deferredResult.setResult(mainSenceService.execute(roomId, userEmail));
        });

        return deferredResult;
    }

    @GetMapping("/room/{roomId}/status/{status}")
    public DeferredResult<GameOutput> roomSence(@PathVariable final int roomId, @PathVariable final String status) {
        DeferredResult<GameOutput> deferredResult = new DeferredResult<>();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(() -> {
            deferredResult.setResult(roomSenceService.execute(roomId, status, userEmail));
        });

        return deferredResult;
    }

    @GetMapping("/play/{roomId}/my/{my}/enemy/{enemy}")
    public DeferredResult<GameOutput> playSence(@PathVariable final int roomId, @PathVariable final int my,
                                                @PathVariable final int enemy) {
        DeferredResult<GameOutput> deferredResult = new DeferredResult<>();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(() -> {
            deferredResult.setResult(playSenceService.execute(roomId, userEmail, my, enemy));
        });

        return deferredResult;
    }

}
