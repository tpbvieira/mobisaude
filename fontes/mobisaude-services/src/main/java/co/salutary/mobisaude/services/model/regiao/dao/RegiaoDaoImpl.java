package co.salutary.mobisaude.services.model.regiao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.services.model.regiao.Regiao;

/**
 * Implementacao de DAO para Regiao
 *
 */
@Repository("regiaoDao")
public class RegiaoDaoImpl implements RegiaoDao {
	/**
	 * EntityManager
	 */
	@PersistenceContext
	
	private EntityManager em;
	/**
	 * GetById
	 * @param idRegiao
	 * @return
	 */
	public Regiao getById(int idRegiao) {
		return em.find(Regiao.class, idRegiao);
	}
	
    /**
     * Listar Regiao
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<Regiao> list() {
		StringBuffer sb = new StringBuffer();
		sb.append("select tes from Regiao tes ");
		Query q = em.createQuery(sb.toString());	
		List<Regiao> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}