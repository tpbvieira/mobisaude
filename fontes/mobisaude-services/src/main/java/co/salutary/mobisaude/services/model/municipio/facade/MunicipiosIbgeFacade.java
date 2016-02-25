package co.salutary.mobisaude.services.model.municipio.facade;

/**
 * Interface implementada pelas fachadas de MunicipiosIbge 
 *
 */
public interface MunicipiosIbgeFacade {
	/**
	 * Buscar o municipio pelas coordenadas
	 * @param lat
	 * @param lng
	 * @return
	 */
	public String[] getCodMunicipioByCoord(Double lat, Double lng);
}
