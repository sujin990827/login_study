package com.cos.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //메모리에 뜨게 해줌
@EnableWebSecurity //활성화 시킴 - 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean //해당 메서드의 리턴되는 오브젝트를 ioc로 등록해준다. 어디서든 쓸 수 있다.
	public BCryptPasswordEncoder encodePwd(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() //유저 하위는 인증 필요
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll() //다른 요청 다 허용함
			.and()
			.formLogin()
			.loginPage("/loginForm")//권한없는 페이지 요청할때 로그인 페이지로 이동시킴
			.loginProcessingUrl("/login") //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행한다
			.defaultSuccessUrl("/");
	}
}
