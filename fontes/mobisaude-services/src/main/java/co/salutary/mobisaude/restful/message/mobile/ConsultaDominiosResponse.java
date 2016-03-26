package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaDominiosResponse")
public class ConsultaDominiosResponse implements MobileResponse {
	
	private String erro;
	private TipoSistemaOperacional[] tipoSistemaOperacional;
	private TipoEstabelecimentoSaudeMsg[] tipoEstabelecimentoSaude;
	private TipoGestaoMsg[] tipoGestaoMsg;
	private RegiaoMsg[] regiaoMsg;

	public String getErro() {
		return erro;
	}
	
	public void setErro(String erro) {
		this.erro = erro;
	}

	public TipoSistemaOperacional[] getTiposSistemaOperacional() {
		return tipoSistemaOperacional;
	}
	
	public void setTiposSistemaOperacional(TipoSistemaOperacional[] tipoSistemaOperacional) {
		this.tipoSistemaOperacional = tipoSistemaOperacional.clone();
	}

	public TipoEstabelecimentoSaudeMsg[] getTiposEstabelecimentoSaude() {
		return tipoEstabelecimentoSaude;
	}
	
	public void setTiposEstabelecimentoSaude(TipoEstabelecimentoSaudeMsg[] tipoEstabelecimentoSaude) {
		this.tipoEstabelecimentoSaude = tipoEstabelecimentoSaude.clone();
	}
	
	public TipoGestaoMsg[] getTiposGestao() {
		return tipoGestaoMsg;
	}

	public void setTipoGestao(TipoGestaoMsg[] tipoGestao) {
		this.tipoGestaoMsg = tipoGestao.clone();
	}

	public RegiaoMsg[] getRegiao() {
		return regiaoMsg;
	}

	public void setRegiao(RegiaoMsg[] regiao) {
		this.regiaoMsg = regiao.clone();
	}
}