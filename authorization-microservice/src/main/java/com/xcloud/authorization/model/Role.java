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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.xcloud.authorization.model.enums.RoleName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role", schema = "public")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 745769242204071301L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "name", nullable = false)
	private RoleName name;

	@ManyToMany(mappedBy = "roles")
	private Set<Group> groups;

}
