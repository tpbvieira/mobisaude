package co.salutary.mobisaude.services.model.tipoestabelecimentosaude.dao;

import java.util.List;

import co.salutary.mobisaude.services.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude;

/**
 * Interface implementada pelos DAOs de TipoEstabelecimentoSaude
 *
 */
public interface TipoEstabelecimentoSaudeDao {
	/**
	 * GetById
	 * @param idTipoEstabelecimentoSaude
	 * @return
	 */
	public TipoEstabelecimentoSaude getById(int idTipoEstabelecimentoSaude);
	/**
	 * Listar os TipoEstabelecimentoSaude
	 * @return
	 */
	public List<TipoEstabelecimentoSaude> list();
}
