package co.salutary.mobisaude.model.estabelecimentosaude.dao;

import java.util.List;

import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;

public interface EstabelecimentoSaudeDao {

    public List<EstabelecimentoSaude> list();
    public List<EstabelecimentoSaude> listByMunicipio(String codMunicipio);
    public List<EstabelecimentoSaude> listByMunicipioTipoEstabelecimento(String codMunicipio, String tipoEstabelecimento);
    public List<EstabelecimentoSaude> listByMunicipioTiposEstabelecimento(String codMunicipio, String[] tiposEstabelecimento);
}
