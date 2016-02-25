package co.salutary.mobisaude.services.model.regiao.dao;

import java.util.List;

import co.salutary.mobisaude.services.model.regiao.Regiao;

/**
 * Interface implementada pelos DAOs de RegiaoMsg
 *
 */
public interface RegiaoDao {
	/**
	 * GetById
	 * @param idRegiao
	 * @return
	 */
	public Regiao getById(int idRegiao);
	/**
	 * Listar 
	 * @return
	 */
	public List<Regiao> list();
}