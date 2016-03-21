package co.salutary.mobisaude.model.tipogestao.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.tipogestao.TipoGestao;
import co.salutary.mobisaude.model.tipogestao.dao.TipoGestaoDao;

/**
 * Implementacao de Facade para TipoGestao
 *
 */
@Service("tipoGestaoFacade")
@Transactional(readOnly = true)
public class TipoGestaoFacadeImpl implements TipoGestaoFacade {
	
	/**
	 * DAO
	 */
	@Autowired
	private TipoGestaoDao tipoGestaoDao;
	
	/**
	 * GetById
	 * @param idTipoGestao
	 * @return
	 */
	public TipoGestao getById(int idTipoGestao) {
		return tipoGestaoDao.getById(idTipoGestao);
	}

	/**
     * Listar os TipoGestao
     * @return
     */
	public List<TipoGestao> list() {
		return tipoGestaoDao.list();
	}
}
