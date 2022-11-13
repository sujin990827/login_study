package com.example.jwt.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;

//uswerName에 해당되는 계정 가져오기 -> userDetails 타입으로 반환
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		User userEntity = userRepository.findByUsername(username);

		//UserService에서 userName으로 DB에서 찾은뒤  userDetails타입으로 반환
		return new PrincipalDetail(userEntity);
	}
}