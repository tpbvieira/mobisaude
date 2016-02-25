package co.salutary.mobisaude.services.model.regiao.facade;

import java.util.List;

import co.salutary.mobisaude.services.model.regiao.Regiao;
/**
 * Interface implementada pelas fachadas de Regiao
 *
 */
public interface RegiaoFacade {

	/**
	 * GetById
	 * @param idRegiao
	 * @return
	 */
	public Regiao getById(int idRegiao);

	/**
	 * Listar Regiao
	 * @return
	 */
	public List<Regiao> list();
}