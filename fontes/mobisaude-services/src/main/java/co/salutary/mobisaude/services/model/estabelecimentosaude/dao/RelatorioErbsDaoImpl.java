package co.salutary.mobisaude.services.model.estabelecimentosaude.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.services.model.estabelecimentosaude.RelatorioErbs;

/**
 * Implementacao de DAO para RelatorioErbs 
 *
 */
@Repository("relatorioErbsDao")
public class RelatorioErbsDaoImpl implements RelatorioErbsDao {
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
	public List<RelatorioErbs> list() {
		Query q = em.createQuery("from RelatorioErbs re ");
		List<RelatorioErbs> result = q.getResultList();
		return result;
	}
    /**
     * Listar os registros por uf, codMunicipioIbte e uma operadora
     * @param uf
     * @param codMunicipio
     * @param operadora
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<RelatorioErbs> listByUfMunicipioOperadora(String uf, String codMunicipio, String operadora) {
		StringBuffer sb = new StringBuffer();
		sb.append("from RelatorioErbs re ");
		sb.append("where 1=1 ");
		if (uf != null && !uf.trim().equals("")) {
			sb.append("and re.uf = :uf ");
		}
		if (codMunicipio != null && !codMunicipio.trim().equals("")) {
			sb.append("and re.codMunicipioIbge = :codMunicipio ");
		}
		if (operadora != null && !operadora.trim().equals("")) {
			sb.append("and upper(re.prestadora) = :operadora ");
		}
		sb.append("order by re.prestadora");
		Query q = em.createQuery(sb.toString());
		if (uf != null && !uf.trim().equals("")) {
			q.setParameter("uf", uf);
		}
		if (codMunicipio != null && !codMunicipio.trim().equals("")) {
			q.setParameter("codMunicipio", codMunicipio);
		}
		if (operadora != null && !operadora.trim().equals("")) {
			q.setParameter("operadora", operadora);
		}
		List<RelatorioErbs> result = q.getResultList();
		return result;
	}
    /**
     * Listar os registros por uf, codMunicipioIbte e uma lista de operadoras
     * @param uf
     * @param codMunicipio
     * @param operadoras
     * @return
     */
	@SuppressWarnings({ "unchecked", "unused" })
	public List<RelatorioErbs> listByUfMunicipioOperadoras(String uf, String codMunicipio, String[] operadoras) {
		StringBuffer sb = new StringBuffer();
		sb.append("from RelatorioErbs re ");
		sb.append("where 1=1 ");
		if (uf != null && !uf.trim().equals("")) {
			sb.append("and re.uf = :uf ");
		}
		if (codMunicipio != null && !codMunicipio.trim().equals("")) {
			sb.append("and re.codMunicipioIbge = :codMunicipio ");
		}
		if (operadoras != null && operadoras.length > 0) {
			int i = 1;
			sb.append("and upper(re.prestadora) in (");
			for (String operadora:operadoras) {
				sb.append(":operadora" + i + ",");
				i++;
			}
			sb.setLength(sb.length()-1);
			sb.append(") ");
		}
		sb.append("order by re.prestadora");
		Query q = em.createQuery(sb.toString());
		if (uf != null && !uf.trim().equals("")) {
			q.setParameter("uf", uf);
		}
		if (codMunicipio != null && !codMunicipio.trim().equals("")) {
			q.setParameter("codMunicipio", codMunicipio);
		}
		int i = 1;
		if (operadoras != null && operadoras.length > 0) {
			for (String operadora:operadoras) {
				q.setParameter("operadora"+i, operadora);
				i++;
			}
		}
		List<RelatorioErbs> result = q.getResultList();
		return result;
	}
}
