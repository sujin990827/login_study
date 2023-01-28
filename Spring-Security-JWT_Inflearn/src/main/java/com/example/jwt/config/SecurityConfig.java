package com.example.jwt.config;

import lombok.RequiredArgsConstructor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;

import com.example.jwt.JwtAuthenticationFilter;
import com.example.jwt.filter.MyFilter1;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends  WebSecurityConfigurerAdapter { //authenticationManager 들고있음

	private final CorsFilter corsFilter;
	// private final UserRepository userRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class)// 스프링시큐리티 필터 시작전이나 시작후로 등록// 필터체인 순서에 맞춰야함
			//.addFilterBefore(new MyFilterFirst(), SecurityContextPersistenceFilter.class)// 처음에 토큰으로 인증을 하는 과정이라 SpringSecurity보다 앞에 작동해야함
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(corsFilter)
			.formLogin().disable()  //JWT 는 아이디 비번 로그인 필요없다
			.httpBasic().disable()
			.addFilter(new JwtAuthenticationFilter(authenticationManager())) //AuthenticationManager 가있어야 userNamePasswordFilter 가능
			//.addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
			.authorizeRequests()
			.antMatchers("/api/v1/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin/**")
			.access(" hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();

	}
}