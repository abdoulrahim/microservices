package com.xcloud.authorization.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.xcloud.authorization.model.enums.GroupName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "security_group", schema = "public")
public class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6812904681472870834L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "name", nullable = false)
	private GroupName name;

	@ManyToMany(mappedBy = "groups")
	private Set<User> users;

	@ManyToMany()
	@JoinTable(name = "group_role", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

}
