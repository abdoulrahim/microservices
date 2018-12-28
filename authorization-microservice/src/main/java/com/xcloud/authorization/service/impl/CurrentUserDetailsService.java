package com.xcloud.authorization.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xcloud.authorization.dto.CurrentUser;
import com.xcloud.authorization.model.User;
import com.xcloud.authorization.service.UserService;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Authenticating user with username={}", username);
		User user = userService.getUserByUsername(username);

		if (user == null)
			user = userService.getUserByEmail(username);

		if (user == null)
			throw new UsernameNotFoundException("User Not Found");

		return new CurrentUser(user);
	}
}
