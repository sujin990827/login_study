//외부와의 통신에 사용할 DTO 클래스를 생성한다
package com.login.jwt.dto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

	@NotNull
	@Size(min=3, max=50)
	private String username;

	@NotNull
	@Size(min=3, max=100)
	private String password;

}
