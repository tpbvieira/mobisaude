package co.salutary.mobisaude.services.model.operadora.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.services.model.operadora.Operadora;
import co.salutary.mobisaude.services.model.operadora.dao.OperadoraDao;

/**
 * Implementacao de Facade para Operadora
 *
 */
@Service("operadoraFacade")
@Transactional(readOnly = true)
public class OperadoraFacadeImpl implements OperadoraFacade {
	/**
	 * DAO
	 */
	@Autowired
	private OperadoraDao operadoraDao;
	/**
	 * GetById
	 * @param idOperadora
	 * @return
	 */
	public Operadora getById(int idOperadora) {
		return operadoraDao.getById(idOperadora);
	}
    /**
     * Listar as operadoras
     * @return
     */
	public List<Operadora> list() {
		return operadoraDao.list();
	}
}
