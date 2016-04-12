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
	public Sugestao getSugestao(Integer idEstabelecimentoSaude, String email) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select s from Sugestao s ");
		queryString.append("where s.idEstabelecimentoSaude = :idEstabelecimentoSaude "
				+ "and s.email = :email");
		Query query = em.createQuery(queryString.toString());	
		
		if (idEstabelecimentoSaude != null) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
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

	public void removeSugestao(Integer idEstabelecimentoSaude, String email) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("delete from Sugestao s ");
		queryString.append("where s.idEstabelecimentoSaude = :idEstabelecimentoSaude "
				+ "and s.email = :email");
		
		Query query = em.createQuery(queryString.toString());
		
		if (idEstabelecimentoSaude != null) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		if (email != null && !email.trim().equals("")) {
			query.setParameter("email", email);
		}
		
		query.executeUpdate();
	}

}