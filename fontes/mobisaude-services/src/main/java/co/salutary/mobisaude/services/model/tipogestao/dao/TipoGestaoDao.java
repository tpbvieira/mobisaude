package co.salutary.mobisaude.services.model.tipogestao.dao;

import java.util.List;

import co.salutary.mobisaude.services.model.tipogestao.TipoGestao;

/**
 * Interface implementada pelos DAOs de TipoGestao
 *
 */
public interface TipoGestaoDao {
	/**
	 * GetById
	 * @param idTipoGestao
	 * @return
	 */
	public TipoGestao getById(int idTipoGestao);
	/**
	 * Listar os TipoGestao
	 * @return
	 */
	public List<TipoGestao> list();
}