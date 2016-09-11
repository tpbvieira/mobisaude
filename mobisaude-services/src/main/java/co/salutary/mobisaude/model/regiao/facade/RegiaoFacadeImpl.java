package co.salutary.mobisaude.model.regiao.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.regiao.Regiao;
import co.salutary.mobisaude.model.regiao.dao.RegiaoDao;

/**
 * Implementacao de Facade para Regiao
 *
 */
@Service("regiaoFacade")
@Transactional(readOnly = true)
public class RegiaoFacadeImpl implements RegiaoFacade {
	
	/**
	 * DAO
	 */
	@Autowired
	private RegiaoDao regiaoDao;
	
	/**
	 * GetById
	 * @param idRegiao
	 * @return
	 */
	public Regiao getById(int idRegiao) {
		return regiaoDao.getById(idRegiao);
	}

	/**
     * Listar os Regiao
     * @return
     */
	public List<Regiao> list() {
		return regiaoDao.list();
	}
}
