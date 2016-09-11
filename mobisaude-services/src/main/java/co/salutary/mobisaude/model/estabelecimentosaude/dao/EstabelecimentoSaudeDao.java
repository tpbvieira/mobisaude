package co.salutary.mobisaude.model.estabelecimentosaude.dao;

import java.util.List;

import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;

public interface EstabelecimentoSaudeDao {

	public EstabelecimentoSaude getByIdES(Integer idES);
    public List<EstabelecimentoSaude> list();
    public List<EstabelecimentoSaude> listByIdMunicipio(String idMunicipio);
    public List<EstabelecimentoSaude> listByIdMunicipioIdTipoEstabelecimento(String idMunicipio, String idTipoEstabelecimento);
    public List<EstabelecimentoSaude> listByIdMunicipioIdTiposES(String idMunicipio, String[] idTiposES);
    
}