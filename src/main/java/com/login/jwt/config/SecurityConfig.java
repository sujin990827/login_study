package com.login.jwt.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.login.jwt.jwt.JwtAccessDeniedHandler;
import com.login.jwt.jwt.JwtAuthenticationEntryPoint;
import com.login.jwt.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity //기본적인 web 보완을 활성화 하겠다
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
class SecurityConfig {

	private final TokenProvider tokenProvider;
//	private final CorsFilter corsFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		http
			// token을 사용하는 방식이기 때문에 csrf를 disable합니다.
			.csrf().disable()

	//		.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			// 세션을 사용하지 않기 때문에 STATELESS로 설정
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			.authorizeRequests()
			.antMatchers("/api/hello").permitAll()
			.antMatchers("/api/authenticate").permitAll()
			.antMatchers("/api/signup").permitAll()

			.anyRequest().authenticated()

			.and()
			.apply(new JwtSecurityConfig(tokenProvider));


		return http.build();
	}

}
