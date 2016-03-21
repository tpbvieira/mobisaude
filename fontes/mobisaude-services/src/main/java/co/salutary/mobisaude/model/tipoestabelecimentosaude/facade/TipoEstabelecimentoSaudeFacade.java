package co.salutary.mobisaude.model.tipoestabelecimentosaude.facade;

import java.util.List;

import co.salutary.mobisaude.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude;
/**
 * Interface implementada pelas fachadas de TipoEstabelecimentoSaude
 *
 */
public interface TipoEstabelecimentoSaudeFacade {
	
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
