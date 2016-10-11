package co.salutary.mobisaude.restful.message.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.salutary.mobisaude.restful.message.mobile.RegiaoDTO;
import co.salutary.mobisaude.restful.message.mobile.TipoESDTO;
import co.salutary.mobisaude.restful.message.mobile.TipoGestaoDTO;
import co.salutary.mobisaude.restful.message.mobile.TipoSistemaOperacionalDTO;

@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaDominiosResponse")
public class ConsultaDominiosResponse implements IMobileResponse {
	
	private String erro;
	private TipoSistemaOperacionalDTO[] tipoSistemaOperacional;
	private TipoESDTO[] tipoES;
	private TipoGestaoDTO[] tipoGestaoMsg;
	private RegiaoDTO[] regiaoMsg;

	public String getErro() {
		return erro;
	}
	
	public void setErro(String erro) {
		this.erro = erro;
	}

	public TipoSistemaOperacionalDTO[] getTiposSistemaOperacional() {
		return tipoSistemaOperacional;
	}
	
	public void setTiposSistemaOperacional(TipoSistemaOperacionalDTO[] tipoSistemaOperacional) {
		this.tipoSistemaOperacional = tipoSistemaOperacional.clone();
	}

	public TipoESDTO[] getTiposES() {
		return tipoES;
	}
	
	public void setTiposES(TipoESDTO[] tipoES) {
		this.tipoES = tipoES.clone();
	}
	
	public TipoGestaoDTO[] getTiposGestao() {
		return tipoGestaoMsg;
	}

	public void setTiposGestao(TipoGestaoDTO[] tipoGestao) {
		this.tipoGestaoMsg = tipoGestao.clone();
	}

	public RegiaoDTO[] getRegiao() {
		return regiaoMsg;
	}

	public void setRegiao(RegiaoDTO[] regiao) {
		this.regiaoMsg = regiao.clone();
	}
	
}