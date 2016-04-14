package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "avaliacaoRequest")
public class AvaliacaoMediaRequest implements IMobileRequest {

	private String token;
	private String idEstabelecimentoSaude;
	private String rating;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
	
	@Override
	public boolean validate() {			
		return idEstabelecimentoSaude != null;
	}
	
}