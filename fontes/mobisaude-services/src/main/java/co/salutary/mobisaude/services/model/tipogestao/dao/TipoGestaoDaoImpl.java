package co.salutary.mobisaude.services.model.tipogestao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.services.model.tipogestao.TipoGestao;

/**
 * Implementacao de DAO para TipoGestao
 *
 */
@Repository("tipoGestaoDao")
public class TipoGestaoDaoImpl implements TipoGestaoDao {
	/**
	 * EntityManager
	 */
	@PersistenceContext
	
	private EntityManager em;
	/**
	 * GetById
	 * @param idTipoGestao
	 * @return
	 */
	public TipoGestao getById(int idTipoGestao) {
		return em.find(TipoGestao.class, idTipoGestao);
	}
	
    /**
     * Listar os TipoGestao
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<TipoGestao> list() {
		StringBuffer sb = new StringBuffer();
		sb.append("select tes from TipoGestao tes ");
		Query q = em.createQuery(sb.toString());	
		List<TipoGestao> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}