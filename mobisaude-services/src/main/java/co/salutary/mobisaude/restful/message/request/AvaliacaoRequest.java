package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.salutary.mobisaude.util.Validator;

@XmlRootElement
@XmlType(namespace = "mobile", name = "avaliacaoRequest")
public class AvaliacaoRequest implements IMobileRequest {

	private String token;
	private String idES;
	private String email;
	private String titulo;
	private String avaliacao;
	private String rating;
	
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Override
	public boolean validate() {			
		return Validator.isValidEmail(email);
	}
	
}
