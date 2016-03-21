package co.salutary.mobisaude.model.tiposistemaoperacional.dao;

import java.util.List;

import co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional;
/**
 * Interface implementada pelos DAOs de TipoSistemaOperacional
 *
 */
public interface TipoSistemaOperacionalDao {
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
