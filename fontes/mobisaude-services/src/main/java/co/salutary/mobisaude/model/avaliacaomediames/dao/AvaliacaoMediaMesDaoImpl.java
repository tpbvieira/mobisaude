package co.salutary.mobisaude.model.avaliacaomediames.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.avaliacaomediames.AvaliacaoMediaMes;

@Repository("avaliacaoMediaDao")
public class AvaliacaoMediaMesDaoImpl implements AvaliacaoMediaMesDao {

	@PersistenceContext
	private EntityManager em;

	public void save(AvaliacaoMediaMes avaliacaoMedia)  {
		em.merge(avaliacaoMedia);			
    }

	@SuppressWarnings("unchecked")
	public AvaliacaoMediaMes getByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select s from AvaliacaoMediaMes s ");
		queryString.append("where s.idEstabelecimentoSaude = :idEstabelecimentoSaude "
				+ "and s.date = :date");
		Query query = em.createQuery(queryString.toString());	
		
		if (idEstabelecimentoSaude != null) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		if (date != null) {
			query.setParameter("date", date);
		}
		
		List<AvaliacaoMediaMes> result = query.getResultList();		
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public void removeByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("delete from AvaliacaoMediaMes s ");
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
	public List<AvaliacaoMediaMes> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from AvaliacaoMediaMes a ");
		queryStr.append("where a.idEstabelecimentoSaude = :idEstabelecimentoSaude ");	
		
		Query query = em.createQuery(queryStr.toString());
		if (idEstabelecimentoSaude != null ) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMediaMes> listByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from AvaliacaoMediaMes a ");
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