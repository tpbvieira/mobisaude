package co.salutary.mobisaude.model.avaliacao.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.avaliacao.Avaliacao;

@Repository("avaliacaoDao")
public class AvaliacaoDaoImpl implements AvaliacaoDao {

	@PersistenceContext
	private EntityManager em;

	public void save(Avaliacao avaliacao)  {
		em.persist(avaliacao);			
    }

	@Override
	@SuppressWarnings("unchecked")
	public Avaliacao getByIdESEmail(Integer idES, String email) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select s from Avaliacao s ");
		queryString.append("where s.idES = :idES "
				+ "and s.email = :email");
		Query query = em.createQuery(queryString.toString());	
		
		if (idES != null) {
			query.setParameter("idES", idES);
		}
		if (email != null && !email.trim().equals("")) {
			query.setParameter("email", email);
		}
		
		List<Avaliacao> result = query.getResultList();
		
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Avaliacao getAvgByIdES(Integer idES) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select avg(a.rating) as rating from Avaliacao a ");
		queryString.append(" where a.idES = :idES ");
		queryString.append(" group by a.idES");
		Query query = em.createQuery(queryString.toString());	
		
		if (idES != null) {
			query.setParameter("idES", idES);
		}
		
		List<Object> result = query.getResultList();
		Avaliacao avaliacao = new Avaliacao();
		if(result != null && result.size() > 0){
			avaliacao.setIdES(idES);
			avaliacao.setRating(Float.parseFloat(result.get(0).toString()));	
		}
		
		return avaliacao;
	}

	@Override
	public void removeByIdESEmail(Integer idES, String email) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("delete from Avaliacao s ");
		queryString.append("where s.idES = :idES "
				+ "and s.email = :email");
		
		Query query = em.createQuery(queryString.toString());
		
		if (idES != null) {
			query.setParameter("idES", idES);
		}
		if (email != null && !email.trim().equals("")) {
			query.setParameter("email", email);
		}
		
		query.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Avaliacao> listByIdES(Integer idES) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from Avaliacao a");
		queryStr.append(" where a.idES = :idES ");
		
		Query query = em.createQuery(queryStr.toString());
		if (idES != null ) {
			query.setParameter("idES", idES);
		}
		
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Avaliacao> listByIdESDate(Integer idES, Date date) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from Avaliacao a ");
		queryStr.append(" where a.idES = :idES");
		queryStr.append(" and a.date = :date ");
		
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