package com.cos.security.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security.domain.User;

import lombok.AllArgsConstructor;

//시큐리티가 /login 주소 요청이 오면 대신 낚아채서 로그인을 진행시킨다
//로그인 진행이 완료가 되면 세션을 만들어 준다 (Security ContextHolder)
//Authentication 안에는 User 정보가 있어야 한다.
// 시큐리티 세션 영역에 세션 정보를 저장하는데, 거기 들어갈 수 있는 객체가 Authentication이다.
// Security Session => Authentication => UserDetails(PrincipalDetails)
@AllArgsConstructor
public class PrincipalDetails implements UserDetails {

	private final User user;

	//해당 유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//유저의 권한은 user.getRole로부터 알 수 있는데 String 타입이라 바로 리턴 못함
		//그래서 보낼 수 있게 타입 바꿔줘야 함
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	//1년동안 로그인 안하면 휴면계정으로 변환
	//현재시간 - 로그인시간 => 1년 초과시 false
	@Override
	public boolean isEnabled() {
		return true;
	}
	//여기까지가 UserDetails를 Authenrication으로 바꾼것
	//그럼 이제 Austhentication을 Security Session으로 바꿔야함
}
