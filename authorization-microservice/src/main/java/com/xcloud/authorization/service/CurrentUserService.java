package com.xcloud.authorization.service;

import com.xcloud.authorization.dto.CurrentUser;

public interface CurrentUserService {

	boolean canAccessUser(CurrentUser currentUser, Long userId);

}
