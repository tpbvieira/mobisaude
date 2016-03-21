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
	private EntityManager entityMngr;

	
	@Override
	@SuppressWarnings("unchecked")
	public List<EstabelecimentoSaude> list() {
		Query query = entityMngr.createQuery("select es from EstabelecimentoSaude es");
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EstabelecimentoSaude> listByMunicipio(String idMunicipio) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from EstabelecimentoSaude es ");
		queryStr.append("where 1=1 ");
		
		if (idMunicipio != null && !idMunicipio.trim().equals("")) {
			queryStr.append("and es.idMunicipio = :idMunicipio ");
		}		
		
		Query query = entityMngr.createQuery(queryStr.toString());
		if (idMunicipio != null && !idMunicipio.trim().equals("")) {
			query.setParameter("idMunicipio", Integer.valueOf(idMunicipio));
		}
		
		return query.getResultList();
	}

	@Override
	public List<EstabelecimentoSaude> listByMunicipioTipoEstabelecimento(String codMunicipio,	String tipoEstabelecimento) {
		String[] tiposEstabelecimento = new String[1];
		tiposEstabelecimento[0] = tipoEstabelecimento;
		return listByMunicipioTiposEstabelecimento(codMunicipio, tiposEstabelecimento);
	}

	@Override
	@SuppressWarnings({ "unchecked", "unused" })
	public List<EstabelecimentoSaude> listByMunicipioTiposEstabelecimento(String idMunicipio, String[] tiposEstabelecimento) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from EstabelecimentoSaude es ");
		queryStr.append("where 1=1 ");
		
		if (idMunicipio != null && !idMunicipio.trim().equals("")) {
			queryStr.append("and es.idMunicipio = :idMunicipio ");
		}
		
		if (tiposEstabelecimento != null && tiposEstabelecimento.length > 0) {
			int i = 1;
			queryStr.append("and es.idTipoEstabelecimentoSaude in (");
			for (String operadora:tiposEstabelecimento) {
				queryStr.append(":tipoEstabelecimentoSaude" + i + ",");
				i++;
			}
			queryStr.setLength(queryStr.length()-1);
			queryStr.append(") ");
		}
		
		queryStr.append("order by es.idTipoEstabelecimentoSaude");
		
		Query query = entityMngr.createQuery(queryStr.toString());
		
		if (idMunicipio != null && !idMunicipio.trim().equals("")) {
			query.setParameter("idMunicipio", Integer.valueOf(idMunicipio));
		}
		
		int i = 1;
		if (tiposEstabelecimento != null && tiposEstabelecimento.length > 0) {
			for (String tipoEstabelecimento:tiposEstabelecimento) {
				query.setParameter("tipoEstabelecimentoSaude"+i, Short.valueOf(tipoEstabelecimento));
				i++;
			}
		}

		return query.getResultList();
	}

}