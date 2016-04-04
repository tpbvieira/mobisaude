package co.salutary.mobisaude.model.estabelecimentosaude.facade;

import java.util.List;

import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;

public interface EstabelecimentoSaudeFacade {

	public List<EstabelecimentoSaude> list();
	public List<EstabelecimentoSaude> listByIdMunicipio(String idMunicipio);
    public List<EstabelecimentoSaude> listByIdMunicipioIdTipoEstabelecimento(String idMunicipio, String idTipoEstabelecimento);
    public List<EstabelecimentoSaude> listByIdMunicipioIdTiposEstabelecimento(String idMunicipio, String[] idTiposEstabelecimento);
    
}
