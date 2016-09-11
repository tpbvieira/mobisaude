package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "avaliacaomediames")
public class AvaliacaoMediaMesDTO implements Cloneable {

	private String idAvaliacaoMediaMes;
	private String idES;
	private String rating; 
	private String date;
	
	public String getIdAvaliacaoMediaMes() {
		return idAvaliacaoMediaMes;
	}
	
	public void setIdAvaliacaoMediaMes(String idAvaliacaoMediaMes) {
		this.idAvaliacaoMediaMes = idAvaliacaoMediaMes;
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

}