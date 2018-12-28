package com.xcloud.authorization.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xcloud.authorization.dao.UserRepository;
import com.xcloud.authorization.dto.UserDto;
import com.xcloud.authorization.model.User;
import com.xcloud.authorization.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Optional<User> getUserById(long id) {
		LOGGER.debug("Getting user={}", id);
		return userRepository.findById(id);
	}

	@Override
	public User getUserByUsername(String username) {
		LOGGER.debug("Getting user by username={}", username);
		return userRepository.findOneByUsername(username);
	}

	@Override
	public User getUserByEmail(String email) {
		LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
		return userRepository.findOneByEmail(email);
	}

	@Override
	public Collection<User> getAllUsers() {
		LOGGER.debug("Getting all users");
		return (Collection<User>) userRepository.findAll();
	}

	@Override
	public User create(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		// user.setRoles(userDto.getRoles());
		return userRepository.save(user);
	}
}
