package login.social.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2Controller {

	@GetMapping({"", "/"})
	public String getAuthorizationMessage(){
		return "home";
	}

	//로그인 화면
	@GetMapping("/login")
	public String login(){
		return "login";
	}

	//로그인 성공
	@GetMapping({"/loginSuccess", "/hello"})
	public String loginSuccess(){
		return "hello";
	}

	//로그인 실패
	@GetMapping("/loginFailure")
	public String loginFailure(){
		return "loginFailure";
	}
}
