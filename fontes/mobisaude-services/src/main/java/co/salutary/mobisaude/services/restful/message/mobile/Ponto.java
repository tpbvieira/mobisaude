package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para conter um ponto no mapa (latitude, longitude).
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "point")
public class Ponto implements Cloneable {
	/**
	 * Campo latitude
	 */
	private String latitude;
	/**
	 * Campo longitude
	 */
	private String longitude;
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
	 * Implementacao de clone
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    Ponto cloned = (Ponto)super.clone();
	    
	    return cloned;
	}
}
