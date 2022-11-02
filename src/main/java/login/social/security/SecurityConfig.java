package login.social.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import login.social.service.CustomOAuth2UserService;
import lombok.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
				.antMatchers("/", "/oauth2/**", "/login/**", "/css/**",
					"/images/**", "/js/**", "console/**", "/favicon.ico/**")
				.permitAll()
				.antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
				.antMatchers("/naver").hasAuthority(NAVER.getRoleType())
				.anyRequest().authenticated()
			.and()
				.oauth2Login()
				.userInfoEndpoint().userService(new CustomOAuth2UserService()) //네이버 User Info 응답을 처리하기 위한 설정
			.and()
				.defaultSuccessUrl("/loginSuccess") //로그인 성공
				.failureUrl("/loginFailure") //로그인 실패
			.and()
				.exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
			return http.build();
				//만약 권한이 없을 때 나온 error는 /login으로 가게끔 반환.
		 		// 스프링부트에서 authenticationEntryPoint 제공하여 form login 템플릿이 아닌 /login 메서드에서 제공하는 템플릿으로 나타나게끔

	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(
			OAuth2ClientProperties oAuth2ClientProperties,
			@Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
			@Value("${custom.oauth2.naver.client-id}") String naverClientId){

	}
	)
}
