package com.login.jwt.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.jwt.domain.User;
import com.login.jwt.dto.UserDto;
import com.login.jwt.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto){
		return ResponseEntity.ok(userService.signup(userDto));
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")  //권한 허용
	public ResponseEntity<User> getMyUserInfo(){
		return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<User> getUserInfo(@PathVariable String username){
		return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
	}


}
