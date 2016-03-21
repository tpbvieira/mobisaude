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

	public List<EstabelecimentoSaude> listByMunicipio(String idMunicipio) {
    	return eSDAO.listByMunicipio(idMunicipio);
    }
	
    public List<EstabelecimentoSaude> listByMunicipioTipoEstabelecimento(String idMunicipio, String tipoEstabelecimento) {
    	return eSDAO.listByMunicipioTipoEstabelecimento(idMunicipio, tipoEstabelecimento);
    }

    public List<EstabelecimentoSaude> listByMunicipioTiposEstabelecimento(String idMunicipio, String[] tiposEstabelecimento) {
    	return eSDAO.listByMunicipioTiposEstabelecimento(idMunicipio, tiposEstabelecimento);
    }
    
}