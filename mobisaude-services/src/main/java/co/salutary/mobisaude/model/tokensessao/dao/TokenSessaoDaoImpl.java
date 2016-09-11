package co.salutary.mobisaude.model.tokensessao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.tokensessao.TokenSessao;


@Repository("tokenSessaoDao")
public class TokenSessaoDaoImpl implements TokenSessaoDao {

	@PersistenceContext
	private EntityManager em;

	public void save(TokenSessao tokenSessao)  {
		em.persist(tokenSessao);			
    }

	@SuppressWarnings("unchecked")
	public TokenSessao getToken(String tokenSessao) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t from TokenSessao t ");
		sb.append("where t.token = :tokenSessao");
		Query q = em.createQuery(sb.toString());	
		if (tokenSessao != null && !tokenSessao.trim().equals("")) {
			q.setParameter("tokenSessao", tokenSessao);
		}
		List<TokenSessao> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public void removeToken(String token) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from TokenSessao t ");
		sb.append("where t.token = :tokenSessao");
		Query q = em.createQuery(sb.toString());
		if (token != null && !token.trim().equals("")) {
			q.setParameter("tokenSessao", token);
		}
		q.executeUpdate();
	}

}