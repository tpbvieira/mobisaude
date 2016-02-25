package co.salutary.mobisaude.controller;

import java.util.List;
import java.util.Map;

import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.Erb;
import co.salutary.mobisaude.model.UF;
import co.salutary.mobisaude.util.DeviceInfo;

public class UserController {
	
    private Cidade cidade;
    private UF uf;
    private Cidade cidadeLocal;
    
    // memoria do Erbs
    private Map<String, Boolean> mapErbsControle = null;
    private List<Erb> listErbs;
    private List<Erb> listHistoricos;

	private static UserController instance = null;

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
    
    public void atualizarCidadeSelecionado(){
    	this.mapErbsControle = null;
    	DeviceInfo.isDadosAtivos = false;
    }
    
    public String getNomeLocalizacao() {
		if(cidade != null){
			return this.cidade.getNome()+"-"+this.uf.getSigla();			
		}
		return "";		
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
	public List<Erb> getListErbs() {
		return listErbs;
	}
	public void setListErbs(List<Erb> listErbs) {
		this.listErbs = listErbs;
	}
	public List<Erb> getListHistoricos() {
		return listHistoricos;
	}
	public void setListHistoricos(List<Erb> listHistoricos) {
		this.listHistoricos = listHistoricos;
	}

}