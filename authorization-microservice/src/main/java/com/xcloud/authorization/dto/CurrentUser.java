package com.xcloud.authorization.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.xcloud.authorization.model.Group;
import com.xcloud.authorization.model.Role;
import com.xcloud.authorization.model.User;
import com.xcloud.authorization.model.enums.RoleName;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9050791967752222502L;

	private User user;

	private String salt;

	public CurrentUser(User user) {
		super(user.getUsername(), user.getPassword(), getAuthorities(user.getGroups()));
		this.user = user;
		this.salt = user.getUsername();
	}

	public String getSalt() {
		return salt;
	}

	public User getUser() {
		return user;
	}

	public Long getId() {
		return user.getId();
	}

	public Collection<Group> getGroups() {
		return user.getGroups();
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Collection<Group> groups) {

		return getGrantedAuthorities(getRoles(groups));
	}

	private static List<String> getRoles(Collection<Group> groups) {

		List<String> roles = new ArrayList<>();
		List<Role> collection = new ArrayList<>();
		for (Group group : groups) {
			collection.addAll(group.getRoles());
		}
		for (Role item : collection) {
			roles.add(item.getName().name());
		}
		return roles;
	}

	private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	public boolean hasRole(RoleName name) {
		return getGroups().stream().map(g -> g.getRoles().stream().filter(r -> r.getName() == name)).count() > 0;
	}

	@Override
	public String toString() {
		return "CurrentUser{" + "user=" + user + "} " + super.toString();
	}

}
