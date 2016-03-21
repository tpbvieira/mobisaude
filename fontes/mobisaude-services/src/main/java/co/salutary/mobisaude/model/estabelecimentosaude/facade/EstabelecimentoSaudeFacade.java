package co.salutary.mobisaude.model.estabelecimentosaude.facade;

import java.util.List;

import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;

public interface EstabelecimentoSaudeFacade {

	public List<EstabelecimentoSaude> list();
	public List<EstabelecimentoSaude> listByMunicipio(String idMunicipio);
    public List<EstabelecimentoSaude> listByMunicipioTipoEstabelecimento(String idMunicipio, String tipoEstabelecimento);
    public List<EstabelecimentoSaude> listByMunicipioTiposEstabelecimento( String idMunicipio, String[] tiposEstabelecimento);
    
}
