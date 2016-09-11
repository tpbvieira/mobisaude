package co.salutary.mobisaude.model.configuracao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.configuracao.Configuracao;

/**
 * Implementacao de DAO para Configuracao
 *
 */
@Repository("configuracaoDao")
public class ConfiguracaoDaoImpl implements ConfiguracaoDao {
	/**
	 * EntityManager
	 */
	@PersistenceContext
	private EntityManager em;
    /**
     * Buscar um configuracao no banco
     * @param configuracao
     * @return
     */
	@SuppressWarnings("unchecked")
	public Configuracao getConfiguracao(String chave) {
		StringBuffer sb = new StringBuffer();
		sb.append("select c from Configuracao c ");
		sb.append("where c.chave = :chave");
		Query q = em.createQuery(sb.toString());	
		if (chave != null && !chave.trim().equals("")) {
			q.setParameter("chave", chave);
		}
		List<Configuracao> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}
}
