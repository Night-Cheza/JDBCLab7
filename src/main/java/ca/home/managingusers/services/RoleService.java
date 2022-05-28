package ca.home.managingusers.services;

import ca.home.managingusers.dataaccess.RoleDB;
import ca.home.managingusers.models.Role;
import java.util.List;


public class RoleService {
	private RoleDB roleDB = new RoleDB();

	public List<Role> getAll() throws Exception {
		List<Role> roles = this.roleDB.getAll();
		return roles;
	}
}
