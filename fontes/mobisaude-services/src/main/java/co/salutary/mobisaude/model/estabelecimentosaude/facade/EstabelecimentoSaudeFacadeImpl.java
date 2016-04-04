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

	public List<EstabelecimentoSaude> list() {
		return eSDAO.list();
	}

	public List<EstabelecimentoSaude> listByIdMunicipio(String idMunicipio) {
    	return eSDAO.listByIdMunicipio(idMunicipio);
    }
	
    public List<EstabelecimentoSaude> listByIdMunicipioIdTipoEstabelecimento(String idMunicipio, String idTipoEstabelecimento) {
    	return eSDAO.listByIdMunicipioIdTipoEstabelecimento(idMunicipio, idTipoEstabelecimento);
    }

    public List<EstabelecimentoSaude> listByIdMunicipioIdTiposEstabelecimento(String idMunicipio, String[] idTiposEstabelecimento) {
    	return eSDAO.listByIdMunicipioIdTiposEstabelecimento(idMunicipio, idTiposEstabelecimento);
    }
    
}