package co.salutary.mobisaude.model.tipoestabelecimentosaude.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.tipoestabelecimentosaude.TipoES;


@Repository("tipoESDao")
public class TipoESDaoImpl implements TipoESDao {

	@PersistenceContext	
	private EntityManager em;

	public TipoES getById(int idTiposES) {
		return em.find(TipoES.class, idTiposES);
	}

	@SuppressWarnings("unchecked")
	public List<TipoES> list() {
		StringBuffer sb = new StringBuffer();
		sb.append("select tes from TipoES tes ");
		Query q = em.createQuery(sb.toString());	
		List<TipoES> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}

}