package co.salutary.mobisaude.model.user.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.user.User;
import co.salutary.mobisaude.model.user.dao.UserDao;

@Service("userFacade")
@Transactional(readOnly = true)
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = false)
	public void save(User user) {
		userDao.save(user);
	}
	
	@Transactional(readOnly = false)
	public User update(User user) {
		return userDao.update(user);
	}

	public User get(String email) {
		return userDao.get(email);
	}

	@Transactional(readOnly = false)
	public int remove(String email) {
		return userDao.remove(email);
	}
	
}