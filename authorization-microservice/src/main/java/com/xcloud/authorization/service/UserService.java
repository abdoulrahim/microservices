package com.xcloud.authorization.service;

import java.util.Collection;
import java.util.Optional;

import com.xcloud.authorization.dto.UserDto;
import com.xcloud.authorization.model.User;

public interface UserService {

	Optional<User> getUserById(long id);

	User getUserByUsername(String username);

	User getUserByEmail(String email);

	Collection<User> getAllUsers();

	User create(UserDto userDto);
}
