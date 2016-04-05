package co.salutary.mobisaude.controller;

import java.util.List;
import java.util.Map;

import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.model.UF;
import co.salutary.mobisaude.util.DeviceInfo;

public class UserController {

    private UF uf;
    private Cidade cidade;
    private Cidade cidadeLocal;
    private EstabelecimentoSaude estabelecimentoSaude;

    private Map<String, Boolean> mapErbsControle = null;
    private List<EstabelecimentoSaude> listEstabelecimentoSaudes;

	private static UserController instance = null;

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
    
    public void atualizarCidadeSelecionado(){
    	this.mapErbsControle = null;
    }
    
    public String getNomeLocalizacao() {
		if(cidade != null){
			return this.cidade.getNome()+"-"+this.uf.getSigla();			
		}
		return "";		
	}

    public EstabelecimentoSaude getEstabelecimentoSaude() {
        return estabelecimentoSaude;
    }
    public void setEstabelecimentoSaude(EstabelecimentoSaude estabelecimentoSaude) {
        this.estabelecimentoSaude = estabelecimentoSaude;
    }
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public UF getUf() {
		return uf;
	}
	public void setUf(UF uf) {
		this.uf = uf;
	}
	public Map<String, Boolean> getMapErbsControle() {
		return mapErbsControle;
	}
	public void setMapErbsControle(Map<String, Boolean> mapErbsControle) {
		this.mapErbsControle = mapErbsControle;
	}
	public Cidade getCidadeLocal() {
		return cidadeLocal;
	}
	public void setCidadeLocal(Cidade cidadeLocal) {
		this.cidadeLocal = cidadeLocal;
	}
	public List<EstabelecimentoSaude> getListEstabelecimentoSaudes() {
		return listEstabelecimentoSaudes;
	}
	public void setListEstabelecimentoSaudes(List<EstabelecimentoSaude> listEstabelecimentoSaudes) {
		this.listEstabelecimentoSaudes = listEstabelecimentoSaudes;
	}

}