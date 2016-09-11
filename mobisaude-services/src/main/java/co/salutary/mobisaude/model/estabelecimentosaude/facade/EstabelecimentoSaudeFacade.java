package co.salutary.mobisaude.model.estabelecimentosaude.facade;

import java.util.List;

import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;

public interface EstabelecimentoSaudeFacade {

	public EstabelecimentoSaude getByIdES(Integer idES);
	public List<EstabelecimentoSaude> list();
	public List<EstabelecimentoSaude> listByIdMunicipio(String idMunicipio);
    public List<EstabelecimentoSaude> listByIdMunicipioIdTipoES(String idMunicipio, String idTipoEstabelecimento);
    public List<EstabelecimentoSaude> listByIdMunicipioIdTiposES(String idMunicipio, String[] idTiposES);
    
}