package com.xcloud.authorization.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xcloud.authorization.dto.UserDto;
import com.xcloud.authorization.service.UserService;

@RestController
@RequestMapping("/users")
@EnableResourceServer
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public Principal getUser(Principal principal) {
		return principal;
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(method = RequestMethod.POST)
	public void createUser(@Valid @RequestBody UserDto user) {
		userService.create(user);
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public Long getUser(@PathVariable String username) {
		return userService.getUserByUsername(username).getId();
//		return "salalmmm1" + username;
	}

}
