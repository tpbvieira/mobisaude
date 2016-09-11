package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "esRequest")
public class ESRequest implements IMobileRequest {
	
	private String token;
	private String idES;
	private String idMunicipio;
	private String idTipoGestao;
	private String idTipoES;	
	private String[] idTiposES;
	
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

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getIdTipoGestao() {
		return idTipoGestao;
	}

	public void setIdTipoGestao(String idTipoGestao) {
		this.idTipoGestao = idTipoGestao;
	}

	public String getIdTipoES() {
		return idTipoES;
	}

	public void setIdTipoES(String idTipoES) {
		this.idTipoES = idTipoES;
	}

	public String[] getIdTiposES() {
		return idTiposES;
	}

	public void setIdTiposES(String[] idTiposES) {
		this.idTiposES = idTiposES;
	}

	@Override
	public boolean validate() {
		if (token == null || token.trim().equals("")) {
			return false;
		}			
		return true;
	}
	
}