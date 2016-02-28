package co.salutary.mobisaude.model.tiposistemaoperacional.facade;

import java.util.List;

import co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional;
/**
 * Interface implementada pelas fachadas de TipoSistemaOperacional
 *
 */
public interface TipoSistemaOperacionalFacade {
	/**
	 * GetById
	 * @param idTipoSistemaOperacional
	 * @return
	 */
	public TipoSistemaOperacional getById(int idTipoSistemaOperacional);
    /**
     * Listar os TipoSistemaOperacional
     * @return
     */
	public List<TipoSistemaOperacional> list();
}
