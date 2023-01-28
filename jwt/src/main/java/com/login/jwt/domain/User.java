package com.login.jwt.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

	@JsonIgnore //데이터를 주고받을 때 해당 데이터는 무시된다. 응답값에 보이지 않음
	@Column(name = "user_id")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name= "username", length = 50, unique = true)
	private String username;

	@Column(name= "password", length = 100)
	private String password;

	@Column(name= "nickname", length = 50)
	private String nickname;

	@JsonIgnore
	@Column(name = "activated")
	private boolean activated;

	@ManyToMany //다대다 관계를 일대다, 다대일 관계의 조인 테이블로 정의했다는 뜻
	@JoinTable(
		name = "user_authority",
		joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
		inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
	)
	private Set<Authority> authorities;
}
