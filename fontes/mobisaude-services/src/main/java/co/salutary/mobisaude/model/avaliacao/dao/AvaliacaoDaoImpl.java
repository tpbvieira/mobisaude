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

	@SuppressWarnings("unchecked")
	public Avaliacao getAvaliacao(Integer idEstabelecimentoSaude, String email) {
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

	public void removeAvaliacao(Integer idEstabelecimentoSaude, String email) {
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
	public List<Avaliacao> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
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
	public List<Avaliacao> listByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
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