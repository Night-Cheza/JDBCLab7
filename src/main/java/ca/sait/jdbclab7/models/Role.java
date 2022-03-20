package ca.sait.jdbclab7.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * Represents a role
 * @author Leila Nalivkina, Nick Hemnett
 */
@Entity
@Table (name="role")
@NamedQueries ({
	@NamedQuery (name = "Role.findAll", query = "SELECT r FROM Role r")
})
public class Role implements Serializable {
	@Id
	@Basic
	@Column (name = "role_id")
	private int roleID;

	@Column (name = "role_name")
	private String roleName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.EAGER)
	private List<User> user;

	public Role() {
	}

	public Role(int roleID, String roleName) {
		this.roleID = roleID;
		this.roleName = roleName;
	}

	public int getId() {
		return roleID;
	}

	public void setId(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
