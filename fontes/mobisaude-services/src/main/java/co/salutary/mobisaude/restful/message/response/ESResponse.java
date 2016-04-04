package co.salutary.mobisaude.restful.message.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.salutary.mobisaude.restful.message.mobile.ESMsg;

@XmlRootElement
@XmlType(namespace = "mobile", name = "esResponse")
public class ESResponse implements IMobileResponse {

	private ESMsg[] esMsg;
	private String erro;

	public ESMsg[] getEstabelecimentoSaude() {
		return esMsg;
	}

	public void setEstabelecimentoSaude(ESMsg[] esMsg) {
		this.esMsg = esMsg.clone();
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

}