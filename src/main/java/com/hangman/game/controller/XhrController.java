package com.hangman.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.hangman.game.service.UserService;

@RestController
@RequestMapping("/xhr")
public class XhrController {

	@Autowired
	private UserService userService;

	@GetMapping("/heartbeat")
	public int heartbeat() {
		int now = (int) (System.currentTimeMillis() / 1000L);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		userService.updateHeartbeat(now, auth.getName());
		return now;
	}

	@GetMapping("/room/{processingTime}")
	public DeferredResult<String> nonBlockingProcessing(
			@RequestParam(value = "processingTime") final long processingTime) throws InterruptedException {
		DeferredResult<String> deferredResult = new DeferredResult<>();
		return deferredResult;
	}
}
