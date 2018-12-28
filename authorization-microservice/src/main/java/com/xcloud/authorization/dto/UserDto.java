package com.xcloud.authorization.dto;

import java.util.Collection;
import java.util.Collections;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.xcloud.authorization.model.Role;

import lombok.Data;

@Data
public class UserDto {

	@NotEmpty
	private String username = "";

	@NotEmpty
	private String email = "";

	@NotEmpty
	private String password = "";

	@NotEmpty
	private String passwordRepeated = "";

	@NotNull
	private Collection<Role> roles = Collections.emptyList();

}
