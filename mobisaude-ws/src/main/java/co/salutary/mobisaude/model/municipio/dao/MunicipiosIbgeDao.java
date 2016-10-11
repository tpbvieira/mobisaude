package co.salutary.mobisaude.model.municipio.dao;

/**
 * Interface implementada pelos DAOs de MunicipiosIbge 
 *
 */
public interface MunicipiosIbgeDao {
	/**
	 * Buscar o municipio pelas coordenadas
	 * @param lat
	 * @param lng
	 * @return
	 */
	public String[] getCodMunicipioByCoord(Double lat, Double lng);
}
