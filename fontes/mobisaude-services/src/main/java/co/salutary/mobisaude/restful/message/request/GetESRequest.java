package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "getESRequest")
public class GetESRequest implements IMobileRequest {
	
	private String token;
	private String municipio;
	private String tipoGestao;
	private String tipoEstabelecimentoSaude;	
	private String[] tiposEstabelecimentoSaude;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getTipoGestao() {
		return tipoGestao;
	}

	public void setTipoGestao(String tipoGestao) {
		this.tipoGestao = tipoGestao;
	}

	public String getTipoEstabelecimentoSaude() {
		return tipoEstabelecimentoSaude;
	}

	public void setTipoEstabelecimentoSaude(String tipoEstabelecimentoSaude) {
		this.tipoEstabelecimentoSaude = tipoEstabelecimentoSaude;
	}

	public String[] getTiposEstabelecimentoSaude() {
		return tiposEstabelecimentoSaude;
	}

	public void setTiposEstabelecimentoSaude(String[] tiposEstabelecimentoSaude) {
		this.tiposEstabelecimentoSaude = tiposEstabelecimentoSaude;
	}

	@Override
	public boolean validar() {
		if (municipio == null || municipio.trim().equals("")) {
			return false;
		} 
			
		return true;
	}
}