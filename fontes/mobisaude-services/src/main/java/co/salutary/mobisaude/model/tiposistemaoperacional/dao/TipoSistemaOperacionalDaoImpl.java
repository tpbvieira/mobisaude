package co.salutary.mobisaude.model.tiposistemaoperacional.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional;

/**
 * Implementacao de DAO para TipoSistemaOperacional
 *
 */
@Repository("tipoSistemaOperacionalDao")
public class TipoSistemaOperacionalDaoImpl implements TipoSistemaOperacionalDao {
	/**
	 * EntityManager
	 */
	@PersistenceContext
	private EntityManager em;
	/**
	 * GetById
	 * @param idTipoSistemaOperacional
	 * @return
	 */
	public TipoSistemaOperacional getById(int idTipoSistemaOperacional) {
		return em.find(TipoSistemaOperacional.class, idTipoSistemaOperacional);
	}
    /**
     * Listar os TipoSistemaOperacional
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<TipoSistemaOperacional> list() {
		StringBuffer sb = new StringBuffer();
		sb.append("select tso from TipoSistemaOperacional tso ");
		Query q = em.createQuery(sb.toString());	
		List<TipoSistemaOperacional> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}
