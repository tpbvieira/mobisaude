package co.salutary.mobisaude.services.model.operadora.dao;

import java.util.List;

import co.salutary.mobisaude.services.model.operadora.Operadora;
/**
 * Interface implementada pelos DAOs de Operadora
 *
 */
public interface OperadoraDao {
	/**
	 * GetById
	 * @param idOperadora
	 * @return
	 */
	public Operadora getById(int idOperadora);	
	/**
	 * Listar as operadoras
	 * @return
	 */
	public List<Operadora> list();
}
