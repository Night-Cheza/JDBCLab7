package ca.home.managingusers.dataaccess;

import ca.home.managingusers.models.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;


public class RoleDB {

	public List<Role> getAll() throws Exception {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();

		try {
			Query query = em.createNamedQuery("Role.findAll");
			return query.getResultList();
		} finally {
			em.close();
		}
	}
}
