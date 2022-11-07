package com.cos.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security.auth.PrincipalDetails;
import com.cos.security.domain.User;
import com.cos.security.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller //뷰를 리턴
public class IndexController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/test/login")
	public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
		System.out.println("/test/login =============");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication: "+ principalDetails.getUser());
		System.out.println("UserDetails: "+ userDetails.getUser());
		return "세션 정보 확인하기";
	}

	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOauthlogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("/test/login =============");
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("authentication: "+ oAuth2User.getAttributes());
		System.out.println("oauth2User: "+oauth.getAttributes());

		return "OAuth 세션 정보 확인하기";
	}

	@GetMapping({"", "/"})
	public String index(){
		//머스테치 기본 폴더: scr/main/resources/
		return "index";
	}

	@GetMapping("/user")
	public String user(){
		return "user";
	}

	@GetMapping("/joinForm")
	public String joinForm(){
		return "joinForm";
	}

	@GetMapping("/admin")
	public String admin(){
		return "admin";
	}

	@GetMapping("/manager")
	public String manager(){
		return "manager";
	}

	@GetMapping("/loginForm")
	public String loginForm(){
		return "loginForm";
	}

	//회원가입
	@PostMapping("/join")
	public String join(User user){
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user);
		return "redirect:/loginForm";
	}
     //responsebody는 글을 출력함

	@Secured("ROLE_ADMIN") //특정메서드만 간단하게 보고싶을때
	@GetMapping("/info")
	public @ResponseBody String info(){
		return "개인정보";
	}

	@PreAuthorize("hasRole('ROLE_MANAGE') or hasRole('ROLE_ADMIN')") //특정메서드만 간단하게 보고싶을때
	@GetMapping("/data")
	public @ResponseBody String data(){
		return "데이터 정보";
	}

}
