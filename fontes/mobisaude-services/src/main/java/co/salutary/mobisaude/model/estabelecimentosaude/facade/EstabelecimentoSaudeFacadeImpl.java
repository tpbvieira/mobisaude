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
	private EstabelecimentoSaudeDao estabelecimentoSaudeDao;

	public List<EstabelecimentoSaude> list() {
		return estabelecimentoSaudeDao.list();
	}

	public List<EstabelecimentoSaude> listByMunicipio(String idMunicipio) {
    	return estabelecimentoSaudeDao.listByMunicipio(idMunicipio);
    }
	
    public List<EstabelecimentoSaude> listByMunicipioTipoEstabelecimento(String idMunicipio, String tipoEstabelecimento) {
    	return estabelecimentoSaudeDao.listByMunicipioTipoEstabelecimento(idMunicipio, tipoEstabelecimento);
    }

    public List<EstabelecimentoSaude> listByMunicipioTiposEstabelecimento(String idMunicipio, String[] tiposEstabelecimento) {
    	return estabelecimentoSaudeDao.listByMunicipioTiposEstabelecimento(idMunicipio, tiposEstabelecimento);
    }
}