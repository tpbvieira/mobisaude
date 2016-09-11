package co.salutary.mobisaude.restful.message.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.salutary.mobisaude.restful.message.mobile.AvaliacaoMediaMesDTO;

@XmlRootElement
@XmlType(namespace = "mobile", name = "avaliacaoMediaResponse")
public class AvaliacaoMediaResponse implements IMobileResponse {

	private String erro;
	private String idES;
	private String rating;
	private String date;
	private List<AvaliacaoMediaMesDTO> avaliacoesMedia;

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

	public List<AvaliacaoMediaMesDTO> getAvaliacoesMediaMes() {
		return avaliacoesMedia;
	}

	public void setAvaliacoesMediaMes(List<AvaliacaoMediaMesDTO> avaliacoesMedia) {
		this.avaliacoesMedia = avaliacoesMedia;
	}
	
}