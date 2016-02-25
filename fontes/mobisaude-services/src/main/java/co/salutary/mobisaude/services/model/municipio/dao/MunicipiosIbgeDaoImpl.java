package co.salutary.mobisaude.services.model.municipio.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.util.Constantes;

/**
 * Implementacao de DAO para MunicipioIbge 
 *
 */
@Repository("municipiosIbgeDao")
public class MunicipiosIbgeDaoImpl implements MunicipiosIbgeDao {
	/**
	 * Persistence context do JPA 
	 */
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Buscar o municipio pelas coordenadas
	 * @param lat
	 * @param lng
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String[] getCodMunicipioByCoord(Double lat, Double lng) {
		StringBuffer sb = new StringBuffer();
		sb.append("select geocodig_m, nome_munic, sigla from ");
		sb.append("municipios_ibge ");
		sb.append("where ST_Contains(municipios_ibge.geom, ST_GeometryFromText('POINT(");
		sb.append(lng);
		sb.append(" ");
		sb.append(lat);
		sb.append(")', " + Constantes.SRID + "))");
		
		Query q = em.createNativeQuery(sb.toString());
		List<Object[]> result = q.getResultList();
		if (result != null && !result.isEmpty()) {
			Object[] linha = result.iterator().next();
			String codMunicipio = ((BigDecimal)linha[0]).toBigInteger().toString();
			String municipio = (String)linha[1];
			String uf = (String)linha[2];
			return new String[]{codMunicipio, municipio, uf};
		} else {
			return null;
		}
	}
}
