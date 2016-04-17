package co.salutary.mobisaude.model.estabelecimentosaude.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;
import co.salutary.mobisaude.model.estabelecimentosaude.dao.EstabelecimentoSaudeDao;

@Service("estabelecimentoSaudeFacade")
@Transactional(readOnly = true)
public class EstabelecimentoSaudeFacadeImpl implements EstabelecimentoSaudeFacade {

	@Autowired
	private EstabelecimentoSaudeDao eSDAO;

	@Override
	public EstabelecimentoSaude getByIdES(Integer idES) {
		return eSDAO.getByIdES(idES);
	}
	
	@Override
	public List<EstabelecimentoSaude> list() {
		return eSDAO.list();
	}

	@Override
	public List<EstabelecimentoSaude> listByIdMunicipio(String idMunicipio) {
    	return eSDAO.listByIdMunicipio(idMunicipio);
    }
	
	@Override
    public List<EstabelecimentoSaude> listByIdMunicipioIdTipoES(String idMunicipio, String idTipoEstabelecimento) {
    	return eSDAO.listByIdMunicipioIdTipoEstabelecimento(idMunicipio, idTipoEstabelecimento);
    }

	@Override
    public List<EstabelecimentoSaude> listByIdMunicipioIdTiposES(String idMunicipio, String[] idTiposES) {
    	return eSDAO.listByIdMunicipioIdTiposES(idMunicipio, idTiposES);
    }
   
}