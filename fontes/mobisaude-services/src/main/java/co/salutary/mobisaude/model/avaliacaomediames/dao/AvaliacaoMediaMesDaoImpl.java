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
	public AvaliacaoMediaMes getByIdESDate(Integer idES, Date date) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select s from AvaliacaoMediaMes s ");
		queryString.append("where s.idES = :idES "
				+ "and s.date = :date");
		Query query = em.createQuery(queryString.toString());	
		
		if (idES != null) {
			query.setParameter("idES", idES);
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

	public void removeByIdESDate(Integer idES, Date date) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("delete from AvaliacaoMediaMes s ");
		queryString.append("where s.idES = :idES "
				+ "and s.date = :date");
		
		Query query = em.createQuery(queryString.toString());
		
		if (idES != null) {
			query.setParameter("idES", idES);
		}
		if (date != null) {
			query.setParameter("date", date);
		}
		
		query.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMediaMes> listByIdES(Integer idES) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from AvaliacaoMediaMes a ");
		queryStr.append("where a.idES = :idES ");	
		
		Query query = em.createQuery(queryStr.toString());
		if (idES != null ) {
			query.setParameter("idES", idES);
		}
		
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMediaMes> listByIdESDate(Integer idES, Date date) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from AvaliacaoMediaMes a ");
		queryStr.append("where a.idES = :idES"
				+ "and s.date = :date");	
		
		Query query = em.createQuery(queryStr.toString());
		if (idES != null ) {
			query.setParameter("idES", idES);
		}
		if (date != null) {
			query.setParameter("date", date);
		}
		
		return query.getResultList();
	}

}