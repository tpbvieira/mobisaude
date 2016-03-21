package co.salutary.mobisaude.model.tiposistemaoperacional.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional;
import co.salutary.mobisaude.model.tiposistemaoperacional.dao.TipoSistemaOperacionalDao;

/**
 * Implementacao de Facade para TipoSistemaOperacional
 *
 */
@Service("tipoSistemaOperacionalFacade")
@Transactional(readOnly = true)
public class TipoSistemaOperacionalFacadeImpl implements TipoSistemaOperacionalFacade {
	/**
	 * DAO
	 */
	@Autowired
	private TipoSistemaOperacionalDao tipoSistemaOperacionalDao;
	/**
	 * GetById
	 * @param idTipoSistemaOperacional
	 * @return
	 */
	public TipoSistemaOperacional getById(int idTipoSistemaOperacional) {
		return tipoSistemaOperacionalDao.getById(idTipoSistemaOperacional);
	}
    /**
     * Listar os TipoSistemaOperacional
     * @return
     */
	public List<TipoSistemaOperacional> list() {
		return tipoSistemaOperacionalDao.list();
	}
}
