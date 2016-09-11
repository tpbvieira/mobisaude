package co.salutary.mobisaude.restful.message.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.salutary.mobisaude.restful.message.mobile.AvaliacaoDTO;

@XmlRootElement
@XmlType(namespace = "mobile", name = "avaliacaoResponse")
public class AvaliacaoResponse implements IMobileResponse {

	private String erro;
	private String idES;
	private String email;
	private String titulo;
	private String avaliacao;
	private String rating;
	private String date;
	private List<AvaliacaoDTO> avaliacoes;
	
	public String getErro() {
		return erro;
	}
	
	public void setErro(String erro) {
		this.erro = erro;
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
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public List<AvaliacaoDTO> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<AvaliacaoDTO> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
}