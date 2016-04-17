package co.salutary.mobisaude.controller;

import java.util.List;
import java.util.Map;

import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.model.UF;

public class UserController {

    private UF uf;
    private Cidade cidade;
    private EstabelecimentoSaude estabelecimentoSaude;
    private List<EstabelecimentoSaude> listEstabelecimentoSaudes;

    private static UserController instance = null;

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public Cidade getCidade() {return cidade;}

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {this.uf = uf;	}

    public List<EstabelecimentoSaude> getListEstabelecimentosSaude() {return listEstabelecimentoSaudes;}

    public void setListEstabelecimentosSaude(List<EstabelecimentoSaude> listEstabelecimentoSaudes) {this.listEstabelecimentoSaudes = listEstabelecimentoSaudes;}

    public EstabelecimentoSaude getEstabelecimentoSaude(){return estabelecimentoSaude;}

    public void setEstabelecimentoSaude(EstabelecimentoSaude es){estabelecimentoSaude = es;}

}