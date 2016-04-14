package co.salutary.mobisaude.model.avaliacaomedia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.avaliacaomedia.AvaliacaoMedia;

@Repository("avaliacaoMediaDao")
public class AvaliacaoMediaDaoImpl implements AvaliacaoMediaDao {

	@PersistenceContext
	private EntityManager em;

	public void save(AvaliacaoMedia avaliacaoMedia)  {
		em.persist(avaliacaoMedia);			
    }

	@SuppressWarnings("unchecked")
	public AvaliacaoMedia getByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select s from AvaliacaoMedia s ");
		queryString.append("where s.idEstabelecimentoSaude = :idEstabelecimentoSaude ");
		Query query = em.createQuery(queryString.toString());	
		
		if (idEstabelecimentoSaude != null) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		
		List<AvaliacaoMedia> result = query.getResultList();		
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public void removeByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("delete from AvaliacaoMedia s ");
		queryString.append("where s.idEstabelecimentoSaude = :idEstabelecimentoSaude ");
		
		Query query = em.createQuery(queryString.toString());
		
		if (idEstabelecimentoSaude != null) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		
		query.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMedia> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from AvaliacaoMedia a ");
		queryStr.append("and es.idEstabelecimentoSaude = :idEstabelecimentoSaude ");	
		
		Query query = em.createQuery(queryStr.toString());
		if (idEstabelecimentoSaude != null ) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		
		return query.getResultList();
	}

}