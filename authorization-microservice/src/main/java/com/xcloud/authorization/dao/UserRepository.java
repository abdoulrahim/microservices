package com.xcloud.authorization.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xcloud.authorization.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findOneByUsername(String username);
	
	User findOneByEmail(String email);

}
