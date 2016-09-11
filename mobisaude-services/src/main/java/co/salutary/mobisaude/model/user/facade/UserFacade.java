package co.salutary.mobisaude.model.user.facade;

import co.salutary.mobisaude.model.user.User;

public interface UserFacade {

	public void save(User user);
	
	public User update(User user);

	public User get(String email);

	public int remove(String email);
	
}