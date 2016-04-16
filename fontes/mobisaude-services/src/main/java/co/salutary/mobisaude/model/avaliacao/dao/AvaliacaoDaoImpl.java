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
	public Avaliacao getByIdEESEmail(Integer idEstabelecimentoSaude, String email) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select s from Avaliacao s ");
		queryString.append("where s.idEstabelecimentoSaude = :idEstabelecimentoSaude "
				+ "and s.email = :email");
		Query query = em.createQuery(queryString.toString());	
		
		if (idEstabelecimentoSaude != null) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
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
	public Avaliacao getAvgByIdEES(Integer idEstabelecimentoSaude) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select avg(a.rating) as rating from Avaliacao a ");
		queryString.append(" where a.idEstabelecimentoSaude = :idEstabelecimentoSaude ");
		queryString.append(" group by a.idEstabelecimentoSaude");
		Query query = em.createQuery(queryString.toString());	
		
		if (idEstabelecimentoSaude != null) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		
		List<Object> result = query.getResultList();
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setIdEstabelecimentoSaude(idEstabelecimentoSaude);
		avaliacao.setRating(Float.parseFloat(result.get(0).toString()));
		return avaliacao;
	}

	@Override
	public void removeByIdEESEmail(Integer idEstabelecimentoSaude, String email) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("delete from Avaliacao s ");
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
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Avaliacao> listByIdES(Integer idEstabelecimentoSaude) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from Avaliacao a");
		queryStr.append(" where a.idEstabelecimentoSaude = :idEstabelecimentoSaude ");
		
		Query query = em.createQuery(queryStr.toString());
		if (idEstabelecimentoSaude != null ) {
			query.setParameter("idEstabelecimentoSaude", idEstabelecimentoSaude);
		}
		
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Avaliacao> listByIdESDate(Integer idEstabelecimentoSaude, Date date) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from Avaliacao a ");
		queryStr.append(" where a.idEstabelecimentoSaude = :idEstabelecimentoSaude");
		queryStr.append(" and a.date = :date ");
		
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