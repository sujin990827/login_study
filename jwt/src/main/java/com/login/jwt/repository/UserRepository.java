package com.login.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.login.jwt.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@EntityGraph(attributePaths = "authorities")
	//해당 쿼리가 수행될때 Lazy조회가 아니고 Eager 조회로 authorities 정보 같이 가져온다
	Optional<User> findOneWithAuthoritiesByUsername(String username);
	//유저이름을 기준으로 유저 정보를 가져올때 권한 정보도 같이 가져오는 메서드
}
