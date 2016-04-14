package co.salutary.mobisaude.model.avaliacaomedia.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.avaliacaomedia.AvaliacaoMedia;
import co.salutary.mobisaude.model.avaliacaomedia.dao.AvaliacaoMediaDao;

@Service("avaliacaoMediaFacade")
@Transactional(readOnly = true)
public class AvaliacaoMediaFacadeImpl implements AvaliacaoMediaFacade {

	@Autowired
	private AvaliacaoMediaDao avaliacaoMediaDao;

	@Transactional(readOnly = false)
	public void save(AvaliacaoMedia avaliacaoMedia) {
		avaliacaoMediaDao.save(avaliacaoMedia);
	}

	public AvaliacaoMedia getByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
		return avaliacaoMediaDao.getByIdEstabelecimentoSaude(idEstabelecimentoSaude);
	}

	public void removeByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
		avaliacaoMediaDao.removeByIdEstabelecimentoSaude(idEstabelecimentoSaude);
	}
	
	public List<AvaliacaoMedia> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
    	return avaliacaoMediaDao.listByIdEstabelecimentoSaude(idEstabelecimentoSaude);
    }
	
}