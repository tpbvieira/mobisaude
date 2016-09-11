package co.salutary.mobisaude.model.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.user.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(User user)  {
		em.persist(user);			
    }
	
	@Override
	public User update(User user) {
		return em.merge(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User get(String email) {

		User user = null;
		
		if (email != null && !email.trim().equals("")) {
			StringBuffer sb = new StringBuffer();
			sb.append("select u from User u ");
			sb.append("where u.email = :email");
			Query query = em.createQuery(sb.toString());
			query.setParameter("email", email);
			List<User> result = query.getResultList();
			if (result != null && !result.isEmpty()) {
				user = result.get(0);
			}
		}
		
		return user;
	}

	@Override
	public int remove(String email) {
		int removed = 0;
		
		if (email != null && !email.trim().equals("")) {
			StringBuffer queryStr = new StringBuffer();
			queryStr.append("delete from User u ");
			queryStr.append("where u.email = :email");
			Query query = em.createQuery(queryStr.toString());
			query.setParameter("email", email);
			removed = query.executeUpdate();
		}
		
		return removed;
	}

}