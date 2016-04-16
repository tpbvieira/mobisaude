package co.salutary.mobisaude.restful.message.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.salutary.mobisaude.restful.message.mobile.AvaliacaoMediaMesDTO;

@XmlRootElement
@XmlType(namespace = "mobile", name = "avaliacaoMediaResponse")
public class AvaliacaoMediaMesResponse implements IMobileResponse {

	private String erro;
	private String idEstabelecimentoSaude;
	private String rating;
	private String date;
	private List<AvaliacaoMediaMesDTO> avaliacoesMedia;

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getIdEstabelecimentoSaude() {
		return idEstabelecimentoSaude;
	}

	public void setIdEstabelecimentoSaude(String idEstabelecimentoSaude) {
		this.idEstabelecimentoSaude = idEstabelecimentoSaude;
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