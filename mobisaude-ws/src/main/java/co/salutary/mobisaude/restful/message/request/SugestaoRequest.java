package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.salutary.mobisaude.util.Validator;

@XmlRootElement
@XmlType(namespace = "mobile", name = "sugestaoRequest")
public class SugestaoRequest implements IMobileRequest {

	private String token;
	private String idES;
	private String email;
	private String sugestao;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSugestao() {
		return sugestao;
	}

	public void setSugestao(String sugestao) {
		this.sugestao = sugestao;
	}

	@Override
	public boolean validate() {			
		return Validator.isValidEmail(email);
	}
	
}
