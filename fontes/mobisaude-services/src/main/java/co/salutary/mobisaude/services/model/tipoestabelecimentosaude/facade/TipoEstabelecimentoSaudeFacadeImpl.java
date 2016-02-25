package co.salutary.mobisaude.services.model.tipoestabelecimentosaude.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.services.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude;
import co.salutary.mobisaude.services.model.tipoestabelecimentosaude.dao.TipoEstabelecimentoSaudeDao;

/**
 * Implementacao de Facade para TipoEstabelecimentoSaude
 *
 */
@Service("tipoEstabelecimentoSaudeFacade")
@Transactional(readOnly = true)
public class TipoEstabelecimentoSaudeFacadeImpl implements TipoEstabelecimentoSaudeFacade {
	
	/**
	 * DAO
	 */
	@Autowired
	private TipoEstabelecimentoSaudeDao tipoEstabelecimentoSaudeDao;
	
	/**
	 * GetById
	 * @param idTipoEstabelecimentoSaude
	 * @return
	 */
	public TipoEstabelecimentoSaude getById(int idTipoEstabelecimentoSaude) {
		return tipoEstabelecimentoSaudeDao.getById(idTipoEstabelecimentoSaude);
	}

	/**
     * Listar os TipoEstabelecimentoSaude
     * @return
     */
	public List<TipoEstabelecimentoSaude> list() {
		return tipoEstabelecimentoSaudeDao.list();
	}
}
