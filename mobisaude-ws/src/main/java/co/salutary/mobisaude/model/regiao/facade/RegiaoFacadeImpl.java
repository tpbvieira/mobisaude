package co.salutary.mobisaude.model.regiao.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.regiao.Regiao;
import co.salutary.mobisaude.model.regiao.dao.RegiaoDao;

@Service("regiaoFacade")
@Transactional(readOnly = true)
public class RegiaoFacadeImpl implements RegiaoFacade {
	
	@Autowired
	private RegiaoDao regiaoDao;
	
	public Regiao getById(int idRegiao) {
		return regiaoDao.getById(idRegiao);
	}

	public List<Regiao> list() {
		return regiaoDao.list();
	}
	
}