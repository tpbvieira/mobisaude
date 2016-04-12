package co.salutary.mobisaude.restful.message.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.salutary.mobisaude.restful.message.mobile.EsDTO;

@XmlRootElement
@XmlType(namespace = "mobile", name = "esResponse")
public class ESResponse implements IMobileResponse {

	private EsDTO[] esMsg;
	private String erro;

	public EsDTO[] getEstabelecimentoSaude() {
		return esMsg;
	}

	public void setEstabelecimentoSaude(EsDTO[] esMsg) {
		this.esMsg = esMsg.clone();
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

}