package com.yqc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yqc.entity.User;

@RestController
public class UserController {

	@GetMapping("/showUser")
	public ResponseEntity<User> showUsers(@RequestParam String userName) {
		User user = new User(userName, 12, "football");
		return ResponseEntity.ok().body(user);
	}
}
