package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do request do servico Geocode
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "geocodeRequest")
public class GeocodeRequest implements IMobileRequest {
	/**
	 * Campo latitude
	 */
	private String latitude;
	/**
	 * Campo longitude
	 */
	private String longitude;
	/**
	 * Campo token
	 */
	private String token;

	/**
	 * Getter de latitude
	 * @return
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Setter de latitude
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * Getter de longitude
	 * @return
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Setter de longitude
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * Getter de token
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Setter de token
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Validar o objeto
	 */
	@Override
	public boolean validar() {
		// Validar campos
		if (latitude == null || latitude.trim().equals("") ||
				longitude == null || longitude.trim().equals("")) {
			return false;
		} 
			
		return true;
	}
}
