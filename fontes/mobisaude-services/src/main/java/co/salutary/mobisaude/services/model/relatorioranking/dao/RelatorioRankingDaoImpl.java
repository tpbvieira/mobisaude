package co.salutary.mobisaude.services.model.relatorioranking.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.services.model.relatorioranking.RelatorioRanking;

/**
 * Implementacao de DAO para RelatorioRanking
 *
 */
@Repository("relatorioRankingDao")
public class RelatorioRankingDaoImpl implements RelatorioRankingDao {
	/**
	 * EntityManager
	 */
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Listar todos registros
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RelatorioRanking> list() {
		Query q = em.createQuery("from RelatorioRanking rr ");
		List<RelatorioRanking> result = q.getResultList();
		return result;
	}
    /**
     * Listar os registros por uf e codMunicipioIbge
     * @param uf
     * @param codMunicipio
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<RelatorioRanking> listByUfMunicipio(String uf, String codMunicipio) {
		StringBuffer sb = new StringBuffer();
		sb.append("from RelatorioRanking rr ");
		sb.append("where 1=1 ");
		if (uf != null && !uf.trim().equals("")) {
			sb.append("and rr.uf = :uf ");
		}
		if (codMunicipio != null && !codMunicipio.trim().equals("")) {
			sb.append("and rr.codMunicipioIbge = :codMunicipio ");
		}
		sb.append("order by rr.prestadora");
		Query q = em.createQuery(sb.toString());
		if (uf != null && !uf.trim().equals("")) {
			q.setParameter("uf", uf);
		}
		if (codMunicipio != null && !codMunicipio.trim().equals("")) {
			q.setParameter("codMunicipio", codMunicipio);
		}
		List<RelatorioRanking> result = q.getResultList();
		return result;
	}
}
