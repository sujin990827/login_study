package com.cos.security.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security.domain.User;
import com.cos.security.repository.UserRepository;

import lombok.AllArgsConstructor;

//시큐리티 설정에서 /login 요청이 오면 자동으로 UserDetailsService 타입으로 IOC 되어있는
//LoadByUsername 함수가 실행된다
@AllArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	//SpringConfig에 username과 동일해야 한다
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//받아온 이름의 유저가 실제로 존재하는지 확인해 봐야한다
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null){
			return new PrincipalDetails(userEntity);
			//리턴해주면 여기서 Authentication내부에 들어간다
		}
		return null;
	}
}
