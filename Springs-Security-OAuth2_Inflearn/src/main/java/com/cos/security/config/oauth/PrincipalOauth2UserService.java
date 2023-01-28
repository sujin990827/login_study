package com.cos.security.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	//로그인 후 후처리되는 함수
	//구글로 부터 받은 userRequest 데이터에 대한 후처리
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest:" +userRequest);
		//구글로그인버튼-> 구글로그인창 -> 로그인완료 -> code리턴(oauth-client)라이브러리 -> 토큰 요청
		//userRequest정보 -> loadUser함수 -> 구글로부터 회원 프로필 받는다
		OAuth2User oAuth2User = super.loadUser(userRequest);


	return super.loadUser(userRequest);
	}
}

