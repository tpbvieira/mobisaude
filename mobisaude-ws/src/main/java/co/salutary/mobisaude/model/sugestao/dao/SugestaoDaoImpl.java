package co.salutary.mobisaude.model.sugestao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.sugestao.Sugestao;

@Repository("sugestaoDao")
public class SugestaoDaoImpl implements SugestaoDao {

	@PersistenceContext
	private EntityManager em;

	public void save(Sugestao sugestao)  {
		em.persist(sugestao);			
    }

	@SuppressWarnings("unchecked")
	public Sugestao getSugestao(Integer idES, String email) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select s from Sugestao s ");
		queryString.append("where s.idES = :idES "
				+ "and s.email = :email");
		Query query = em.createQuery(queryString.toString());	
		
		if (idES != null) {
			query.setParameter("idES", idES);
		}
		if (email != null && !email.trim().equals("")) {
			query.setParameter("email", email);
		}
		
		List<Sugestao> result = query.getResultList();
		
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public void removeSugestao(Integer idES, String email) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("delete from Sugestao s ");
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

    @SuppressWarnings("unchecked")
	public List<Sugestao> list() {
		StringBuffer sb = new StringBuffer();
		sb.append("select s from Sugestao s ");
		Query q = em.createQuery(sb.toString());	
		List<Sugestao> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
	
}