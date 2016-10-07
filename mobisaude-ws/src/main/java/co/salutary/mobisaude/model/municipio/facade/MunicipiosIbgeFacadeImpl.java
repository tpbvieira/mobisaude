package co.salutary.mobisaude.model.municipio.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.municipio.dao.MunicipiosIbgeDao;

/**
 * Implementacao de Facade para MunicipioIbge 
 *
 */
@Service("municipiosIbgeFacade")
@Transactional(readOnly = true)
public class MunicipiosIbgeFacadeImpl implements MunicipiosIbgeFacade {
	/**
	 * DAO 
	 */
	@Autowired
	private MunicipiosIbgeDao municipiosIbgeDao;

	/**
	 * Buscar o municipio pelas coordenadas
	 * @param lat
	 * @param lng
	 * @return
	 */
	public String[] getCodMunicipioByCoord(Double lat, Double lng) {
		return municipiosIbgeDao.getCodMunicipioByCoord(lat, lng);
	}
}
