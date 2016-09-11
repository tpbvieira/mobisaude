package co.salutary.mobisaude.restful.message.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "gerarTokenResponse")
public class GerarTokenResponse implements IMobileResponse {

	private String token;
	private String erro;

	public GerarTokenResponse(){
		token = null;
		erro = null;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

}