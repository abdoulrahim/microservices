package com.xcloud.authorization.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xcloud.authorization.dto.CurrentUser;
import com.xcloud.authorization.model.enums.RoleName;
import com.xcloud.authorization.service.CurrentUserService;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

	@Override
	public boolean canAccessUser(CurrentUser currentUser, Long userId) {
		LOGGER.debug("Checking if user={} has access to user={}", currentUser, userId);
		return currentUser != null && (currentUser.hasRole(RoleName.ADMIN_ROLE) || currentUser.getId().equals(userId));
	}

}
