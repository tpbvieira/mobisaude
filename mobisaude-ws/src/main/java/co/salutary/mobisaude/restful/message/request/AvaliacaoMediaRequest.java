package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "avaliacaoMediaRequest")
public class AvaliacaoMediaRequest implements IMobileRequest {

	private String token;
	private String idES;
	private String rating;
	private String date;
	private String siglaUF;
	private String idMunicipio;
	private String idTipoES;
		
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIdES() {
		return idES;
	}

	public void setIdES(String idES) {
		this.idES = idES;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
	@Override
	public boolean validate() {			
		return idES != null;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSiglaUF() {
		return siglaUF;
	}

	public void setSiglaUF(String siglaUF) {
		this.siglaUF = siglaUF;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getIdTipoES() {
		return idTipoES;
	}

	public void setIdTipoES(String idTipoES) {
		this.idTipoES = idTipoES;
	}
		
}