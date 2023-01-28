package com.login.jwt.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "authority")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Authority {

	@Id
	@Column(name = "authority_name", length = 50)
	private String authorityName;
}
