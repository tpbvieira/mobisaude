package co.salutary.mobisaude.model.regiao.dao;

import java.util.List;

import co.salutary.mobisaude.model.regiao.Regiao;

/**
 * Interface implementada pelos DAOs de Regiao
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