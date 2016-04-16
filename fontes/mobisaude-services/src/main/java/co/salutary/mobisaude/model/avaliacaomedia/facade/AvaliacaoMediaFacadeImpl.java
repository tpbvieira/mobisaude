package co.salutary.mobisaude.model.avaliacaomedia.facade;

import java.util.Date;
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

	public AvaliacaoMedia getByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		return avaliacaoMediaDao.getByIdEstabelecimentoSaudeDate(idEstabelecimentoSaude,date);
	}

	public void removeByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		avaliacaoMediaDao.removeByIdEstabelecimentoSaudeDate(idEstabelecimentoSaude,date);
	}
	
	public List<AvaliacaoMedia> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
    	return avaliacaoMediaDao.listByIdEstabelecimentoSaude(idEstabelecimentoSaude);
    }
	
	public List<AvaliacaoMedia> listByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
    	return avaliacaoMediaDao.listByIdEstabelecimentoSaudeDate(idEstabelecimentoSaude, date);
    }
}