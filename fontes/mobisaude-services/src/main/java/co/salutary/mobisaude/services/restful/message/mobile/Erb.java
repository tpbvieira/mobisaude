package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para conter uma antena (ERB).
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "erb")
public class Erb implements Cloneable {
	/**
	 * Campo uf
	 */
	private String uf;
	/**
	 * Campo codMunicipioIbge
	 */
	private String codMunicipioIbge;
	/**
	 * Campo municipio
	 */
	private String municipio;
	/**
	 * Campo prestadora
	 */
	private String prestadora;
	/**
	 * Campo nomeFantasia
	 */
	private String nomeFantasia;
	/**
	 * Campo tecnologia2g
	 */
	private String tecnologia2g;
	/**
	 * Campo tecnologia3g
	 */
	private String tecnologia3g;
	/**
	 * Campo tecnologia4g
	 */
	private String tecnologia4g;
	/**
	 * Campo latitudeStel
	 */
	private String latitudeStel;
	/**
	 * Campo longitudeStel
	 */
	private String longitudeStel;

	/**
	 * Getter de uf
	 * @return
	 */
	public String getUf() {
		return uf;
	}
	/**
	 * Setter de uf
	 * @param uf
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}
	/**
	 * Getter de codMunicipioIbge
	 * @return
	 */
	public String getCodMunicipioIbge() {
		return codMunicipioIbge;
	}
	/**
	 * Setter de codMunicipioIbge
	 * @param codMunicipioIbge
	 */
	public void setCodMunicipioIbge(String codMunicipioIbge) {
		this.codMunicipioIbge = codMunicipioIbge;
	}
	/**
	 * Getter de municipio
	 * @return
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * Setter de municipio
	 * @param municipio
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	/**
	 * Getter de prestadora
	 * @return
	 */
	public String getPrestadora() {
		return prestadora;
	}
	/**
	 * Setter de prestadora
	 * @param prestadora
	 */
	public void setPrestadora(String prestadora) {
		this.prestadora = prestadora;
	}
	/**
	 * Getter de nomeFantasia
	 * @return
	 */
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	/**
	 * Setter de nomeFantasia
	 * @param nomeFantasia
	 */
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	/**
	 * Getter de tecnologia2g
	 * @return
	 */
	public String getTecnologia2g() {
		return tecnologia2g;
	}
	/**
	 * Setter de tecnologia2g
	 * @param tecnologia2g
	 */
	public void setTecnologia2g(String tecnologia2g) {
		this.tecnologia2g = tecnologia2g;
	}
	/**
	 * Getter de tecnologia3g
	 * @return
	 */
	public String getTecnologia3g() {
		return tecnologia3g;
	}
	/**
	 * Setter de tecnologia3g
	 * @param tecnologia3g
	 */
	public void setTecnologia3g(String tecnologia3g) {
		this.tecnologia3g = tecnologia3g;
	}
	/**
	 * Getter de tecnologia4g
	 * @return
	 */
	public String getTecnologia4g() {
		return tecnologia4g;
	}
	/**
	 * Setter de tecnologia4g
	 * @param tecnologia4g
	 */
	public void setTecnologia4g(String tecnologia4g) {
		this.tecnologia4g = tecnologia4g;
	}
	/**
	 * Getter de latitudeStel
	 * @return
	 */
	public String getLatitudeStel() {
		return latitudeStel;
	}
	/**
	 * Setter de latitudeStel
	 * @param latitudeStel
	 */
	public void setLatitudeStel(String latitudeStel) {
		this.latitudeStel = latitudeStel;
	}
	/**
	 * Getter de longitudeStel
	 * @return
	 */
	public String getLongitudeStel() {
		return longitudeStel;
	}
	/**
	 * Setter de longitudeStel
	 * @param longitudeStel
	 */
	public void setLongitudeStel(String longitudeStel) {
		this.longitudeStel = longitudeStel;
	}
	
	/**
	 * Implementacao de clone
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    Erb cloned = (Erb)super.clone();
	    
	    return cloned;
	}
}
