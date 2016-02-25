package co.salutary.mobisaude.services.model.tipoestabelecimentosaude.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.services.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude;

/**
 * Implementacao de DAO para TipoEstabelecimentoSaude
 *
 */
@Repository("tipoEstabelecimentoSaudeDao")
public class TipoEstabelecimentoSaudeDaoImpl implements TipoEstabelecimentoSaudeDao {
	/**
	 * EntityManager
	 */
	@PersistenceContext
	
	private EntityManager em;
	/**
	 * GetById
	 * @param idTipoEstabelecimentoSaude
	 * @return
	 */
	public TipoEstabelecimentoSaude getById(int idTipoEstabelecimentoSaude) {
		return em.find(TipoEstabelecimentoSaude.class, idTipoEstabelecimentoSaude);
	}
	
    /**
     * Listar os TipoEstabelecimentoSaude
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<TipoEstabelecimentoSaude> list() {
		StringBuffer sb = new StringBuffer();
		sb.append("select tes from TipoEstabelecimentoSaude tes ");
		Query q = em.createQuery(sb.toString());	
		List<TipoEstabelecimentoSaude> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
}
