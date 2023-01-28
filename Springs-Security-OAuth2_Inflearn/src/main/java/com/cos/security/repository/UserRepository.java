package com.cos.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security.domain.User;

//CRUD 함수를 JpaRepository가 들고 있다
public interface UserRepository extends JpaRepository<User, Integer> {

	//findBy규칙 -> Username 문법
	public User findByUsername(String username);

}
