package co.salutary.mobisaude.model.operadora.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.operadora.Operadora;

/**
 * Implementacao de DAO para Operadora
 *
 */
@Repository("operadoraDao")
public class OperadoraDaoImpl implements OperadoraDao {
	/**
	 * EntityManager
	 */
	@PersistenceContext
	private EntityManager em;
	/**
	 * GetById
	 * @param idOperadora
	 * @return
	 */
	public Operadora getById(int idOperadora) {
		return em.find(Operadora.class, idOperadora);
	}
    /**
     * Listar as operadoras
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<Operadora> list() {
		StringBuffer sb = new StringBuffer();
		sb.append("select o from Operadora o ");
		Query q = em.createQuery(sb.toString());	
		List<Operadora> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}
