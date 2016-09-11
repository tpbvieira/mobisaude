package co.salutary.mobisaude.controller;

import java.util.List;

import co.salutary.mobisaude.model.Avaliacao;
import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.model.UF;

public class ClientCache {

    private UF uf;
    private Cidade cidade;
    private EstabelecimentoSaude estabelecimentoSaude;
    private List<EstabelecimentoSaude> listEstabelecimentosSaudeCidade;
    private List<EstabelecimentoSaude> listEstabelecimentosSaudeTipoES;
    private Avaliacao avaliacaoMedia;
    private List<Avaliacao> listAvaliacoes;

    private static ClientCache instance = null;

    public static ClientCache getInstance() {
        if (instance == null) {
            instance = new ClientCache();
        }
        return instance;
    }

    public Cidade getCidade() {return cidade;}

    public void setCidade(Cidade cidade) {this.cidade = cidade;}

    public UF getUf() {return uf;}

    public void setUf(UF uf) {this.uf = uf;	}

    public List<EstabelecimentoSaude> getListEstabelecimentosSaudeCidade() {return listEstabelecimentosSaudeCidade;}

    public void setListEstabelecimentosSaudeCidade(List<EstabelecimentoSaude> listEstabelecimentosSaudeCidade) {this.listEstabelecimentosSaudeCidade = listEstabelecimentosSaudeCidade;}

    public List<EstabelecimentoSaude> getListEstabelecimentosSaudeTipoES() {return listEstabelecimentosSaudeTipoES;}

    public void setListEstabelecimentosSaudeTipoES(List<EstabelecimentoSaude> listEstabelecimentosSaudeTipoES) {this.listEstabelecimentosSaudeTipoES = listEstabelecimentosSaudeTipoES;}

    public EstabelecimentoSaude getEstabelecimentoSaude(){return estabelecimentoSaude;}

    public void setEstabelecimentoSaude(EstabelecimentoSaude es){estabelecimentoSaude = es;}

    public List<Avaliacao> getListAvaliacoes() {return listAvaliacoes;}

    public void setListAvaliacoes(List<Avaliacao> listAvaliacoes) {this.listAvaliacoes = listAvaliacoes;}

    public Avaliacao getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void setAvaliacaoMedia(Avaliacao avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }

}