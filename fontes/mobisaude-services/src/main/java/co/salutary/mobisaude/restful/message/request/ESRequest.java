package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "esRequest")
public class ESRequest implements IMobileRequest {
	
	private String token;
	private String idMunicipio;
	private String idTipoGestao;
	private String idTipoEstabelecimentoSaude;	
	private String[] idTiposEstabelecimentoSaude;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getIdTipoEstabelecimentoSaude() {
		return idTipoEstabelecimentoSaude;
	}

	public void setIdTipoEstabelecimentoSaude(String idTipoEstabelecimentoSaude) {
		this.idTipoEstabelecimentoSaude = idTipoEstabelecimentoSaude;
	}

	public String[] getIdTiposEstabelecimentoSaude() {
		return idTiposEstabelecimentoSaude;
	}

	public void setIdTiposEstabelecimentoSaude(String[] idTiposEstabelecimentoSaude) {
		this.idTiposEstabelecimentoSaude = idTiposEstabelecimentoSaude;
	}

	@Override
	public boolean validate() {
		if (token == null || token.trim().equals("")) {
			return false;
		}			
		return true;
	}
	
}