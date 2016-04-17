package co.salutary.mobisaude.model.estabelecimentosaude.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;


@Repository("estabelecimentoSaudeDao")
public class EstabelecimentoSaudeDaoImpl implements EstabelecimentoSaudeDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public EstabelecimentoSaude getByIdES(Integer idES) {
		return em.find(EstabelecimentoSaude.class, idES);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EstabelecimentoSaude> list() {
		Query query = em.createQuery("select es from EstabelecimentoSaude es");
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EstabelecimentoSaude> listByIdMunicipio(String idMunicipio) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from EstabelecimentoSaude es ");
		queryStr.append("where 1=1 ");
		
		if (idMunicipio != null && !idMunicipio.trim().equals("")) {
			queryStr.append("and es.idMunicipio = :idMunicipio ");
		}		
		
		Query query = em.createQuery(queryStr.toString());
		if (idMunicipio != null && !idMunicipio.trim().equals("")) {
			query.setParameter("idMunicipio", Integer.valueOf(idMunicipio));
		}
		
		return query.getResultList();
	}

	@Override
	public List<EstabelecimentoSaude> listByIdMunicipioIdTipoEstabelecimento(String idMunicipio, String idTipoEstabelecimento) {
		String[] idTiposES = new String[1];
		idTiposES[0] = idTipoEstabelecimento;
		return listByIdMunicipioIdTiposES(idMunicipio, idTiposES);
	}

	@Override
	@SuppressWarnings({ "unchecked", "unused" })
	public List<EstabelecimentoSaude> listByIdMunicipioIdTiposES(String idMunicipio, String[] idTiposES) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from EstabelecimentoSaude es ");
		queryStr.append("where 1=1 ");
		
		if (idMunicipio != null && !idMunicipio.trim().equals("")) {
			queryStr.append("and es.idMunicipio = :idMunicipio ");
		}
		
		if (idTiposES != null && idTiposES.length > 0) {
			int i = 1;
			queryStr.append("and es.idTiposES in (");
			for (String idTipoEstabelecimento:idTiposES) {
				queryStr.append(":idTiposES" + i + ",");
				i++;
			}
			queryStr.setLength(queryStr.length()-1);
			queryStr.append(") ");
		}
		
		queryStr.append("order by es.idTiposES");
		
		Query query = em.createQuery(queryStr.toString());
		
		if (idMunicipio != null && !idMunicipio.trim().equals("")) {
			query.setParameter("idMunicipio", Integer.valueOf(idMunicipio));
		}
		
		int i = 1;
		if (idTiposES != null && idTiposES.length > 0) {
			for (String idTipoEstabelecimento:idTiposES) {
				query.setParameter("idTiposES"+i, Short.valueOf(idTipoEstabelecimento));
				i++;
			}
		}

		return query.getResultList();
	}

}