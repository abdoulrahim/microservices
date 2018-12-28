package com.xcloud.authorization.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xcloud.authorization.dao.RoleRepository;
import com.xcloud.authorization.model.Role;
import com.xcloud.authorization.service.RoleService;

public class RoleServiceImpl implements RoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	RoleRepository roleRepository;

	@Override
	public Role getRoleByName(String name) {
		LOGGER.debug("Getting role by name={}", name);
		return roleRepository.findOneByName(name);
	}

}
