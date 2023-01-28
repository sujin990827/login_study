//회원가입시 사용할 DTO 클래스를 생성한다
package com.login.jwt.dto;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.login.jwt.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@NotNull
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull
	private String password;

	@NotNull
	private String nickname;


}
