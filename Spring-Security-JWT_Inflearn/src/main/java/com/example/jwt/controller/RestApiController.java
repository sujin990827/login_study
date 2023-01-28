package com.example.jwt.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class RestApiController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/home")
	public String home(){
		return"home";
	}

	@PostMapping("/token")
	public String token(){
		return"token";
	}

	@PostMapping("join")
	public String join(@RequestBody User user){

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		return "회원가입 완료";
	}

}
