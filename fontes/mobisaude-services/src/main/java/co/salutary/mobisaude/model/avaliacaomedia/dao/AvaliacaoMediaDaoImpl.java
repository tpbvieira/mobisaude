package co.salutary.mobisaude.model.avaliacaomedia.dao;

import java.util.Date;
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
		em.merge(avaliacaoMedia);			
    }

	@SuppressWarnings("unchecked")
	public AvaliacaoMedia getByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select s from AvaliacaoMedia s ");
		queryString.append("where s.idEstabelecimentoSaude = :idEstabelecimentoSaude "
				+ "and s.date = :date");
		Query query = em.createQuery(queryString.toString());	
		
		if (idEstabelecimentoSaude != null) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		if (date != null) {
			query.setParameter("date", date);
		}
		
		List<AvaliacaoMedia> result = query.getResultList();		
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public void removeByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("delete from AvaliacaoMedia s ");
		queryString.append("where s.idEstabelecimentoSaude = :idEstabelecimentoSaude "
				+ "and s.date = :date");
		
		Query query = em.createQuery(queryString.toString());
		
		if (idEstabelecimentoSaude != null) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		if (date != null) {
			query.setParameter("date", date);
		}
		
		query.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMedia> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from AvaliacaoMedia a ");
		queryStr.append("where a.idEstabelecimentoSaude = :idEstabelecimentoSaude ");	
		
		Query query = em.createQuery(queryStr.toString());
		if (idEstabelecimentoSaude != null ) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMedia> listByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from AvaliacaoMedia a ");
		queryStr.append("where a.idEstabelecimentoSaude = :idEstabelecimentoSaude"
				+ "and s.date = :date");	
		
		Query query = em.createQuery(queryStr.toString());
		if (idEstabelecimentoSaude != null ) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		if (date != null) {
			query.setParameter("date", date);
		}
		
		return query.getResultList();
	}

}