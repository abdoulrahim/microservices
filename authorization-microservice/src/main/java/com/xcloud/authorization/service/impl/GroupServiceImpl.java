package com.xcloud.authorization.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xcloud.authorization.dao.GroupRepository;
import com.xcloud.authorization.model.Group;
import com.xcloud.authorization.service.GroupService;

public class GroupServiceImpl implements GroupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Autowired
	GroupRepository groupRepository;

	@Override
	public Group getGroupByName(String name) {
		LOGGER.debug("Getting group by name={}", name);
		return groupRepository.findOneByName(name);
	}

}
