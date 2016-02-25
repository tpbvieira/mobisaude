package co.salutary.mobisaude.services.model.tipogestao.facade;

import java.util.List;

import co.salutary.mobisaude.services.model.tipogestao.TipoGestao;
/**
 * Interface implementada pelas fachadas de TipoGestao
 *
 */
public interface TipoGestaoFacade {

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