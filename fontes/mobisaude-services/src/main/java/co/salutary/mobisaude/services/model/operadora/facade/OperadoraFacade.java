package co.salutary.mobisaude.services.model.operadora.facade;

import java.util.List;

import co.salutary.mobisaude.services.model.operadora.Operadora;
/**
 * Interface implementada pelas fachadas de Operadora
 *
 */
public interface OperadoraFacade {
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
