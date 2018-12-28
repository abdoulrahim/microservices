package com.xcloud.authorization.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xcloud.authorization.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findOneByName(String name);
}
