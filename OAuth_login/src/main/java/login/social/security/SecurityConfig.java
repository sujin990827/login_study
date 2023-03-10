package login.social.security;

import static login.social.security.SocialType.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import login.social.service.CustomOAuth2UserService;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final OAuth2ClientProperties oAuth2ClientProperties;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
			.antMatchers("/", "/oauth2/**", "/login/**", "/css/**",
				"/images/**", "/js/**", "/console/**", "/favicon.ico/**")
				.permitAll()
				.antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
				.antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
				.antMatchers("/naver").hasAuthority(NAVER.getRoleType())
				.anyRequest().authenticated()
			.and()
				.oauth2Login()
				.userInfoEndpoint().userService(new CustomOAuth2UserService())  // ????????? USER INFO??? ????????? ???????????? ?????? ??????
			.and()
				.defaultSuccessUrl("/loginSuccess")
				.failureUrl("/loginFailure")
			.and()
				.exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(
			@Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
			@Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
			@Value("${custom.oauth2.naver.client-id}") String naverClientId,
			@Value("${custom.oauth2.naver.client-secret}") String naverClientSecret) {
		List<ClientRegistration> registrations = oAuth2ClientProperties
			.getRegistration().keySet().stream()
			.map(client -> getRegistration(oAuth2ClientProperties, client))
			.filter(Objects::nonNull)
			.collect(Collectors.toList());

		registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
			.clientId(kakaoClientId)
			.clientSecret(kakaoClientSecret)
			.jwkSetUri("temp")
			.build());

		registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
			.clientId(naverClientId)
			.clientSecret(naverClientSecret)
			.jwkSetUri("temp")
			.build());
		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
		if("google".equals(client)) {
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
			return CommonOAuth2Provider.GOOGLE.getBuilder(client)
				.clientId(registration.getClientId())
				.clientSecret(registration.getClientSecret())
				.scope("email", "profile")
				.build();
		}


		return null;
	}
}