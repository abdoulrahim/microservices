package com.xcloud.authorization.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xcloud.authorization.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	Group findOneByName(String name);
}
