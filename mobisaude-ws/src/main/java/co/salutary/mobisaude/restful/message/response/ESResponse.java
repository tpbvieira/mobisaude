package co.salutary.mobisaude.restful.message.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.salutary.mobisaude.restful.message.mobile.EsDTO;

@XmlRootElement
@XmlType(namespace = "mobile", name = "esResponse")
public class ESResponse implements IMobileResponse {

	private EsDTO[] estabelecimentosSaude;
	private String erro;

	public EsDTO[] getEstabelecimentosSaude() {
		return estabelecimentosSaude;
	}

	public void setEstabelecimentosSaude(EsDTO[] esMsg) {
		this.estabelecimentosSaude = esMsg.clone();
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

}