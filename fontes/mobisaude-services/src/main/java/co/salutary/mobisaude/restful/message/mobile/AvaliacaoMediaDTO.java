package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "avaliacaomedia")
public class AvaliacaoMediaDTO implements Cloneable {

	private String idAvaliacaoMedia;
	private String idEstabelecimentoSaude;
	private String rating; 
	private String date;
	
	public String getIdAvaliacaoMedia() {
		return idAvaliacaoMedia;
	}
	
	public void setIdAvaliacaoMedia(String idAvaliacaoMedia) {
		this.idAvaliacaoMedia = idAvaliacaoMedia;
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

}