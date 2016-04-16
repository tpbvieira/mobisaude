package co.salutary.mobisaude.model.avaliacaomediames.facade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.avaliacaomediames.AvaliacaoMediaMes;
import co.salutary.mobisaude.model.avaliacaomediames.dao.AvaliacaoMediaMesDao;

@Service("avaliacaoMediaMesFacade")
@Transactional(readOnly = true)
public class AvaliacaoMediaMesFacadeImpl implements AvaliacaoMediaMesFacade {

	@Autowired
	private AvaliacaoMediaMesDao avaliacaoMediaDao;

	@Transactional(readOnly = false)
	public void save(AvaliacaoMediaMes avaliacaoMedia) {
		avaliacaoMediaDao.save(avaliacaoMedia);
	}

	public AvaliacaoMediaMes getByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		return avaliacaoMediaDao.getByIdEstabelecimentoSaudeDate(idEstabelecimentoSaude,date);
	}

	public void removeByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
		avaliacaoMediaDao.removeByIdEstabelecimentoSaudeDate(idEstabelecimentoSaude,date);
	}
	
	public List<AvaliacaoMediaMes> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
    	return avaliacaoMediaDao.listByIdEstabelecimentoSaude(idEstabelecimentoSaude);
    }
	
	public List<AvaliacaoMediaMes> listByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
    	return avaliacaoMediaDao.listByIdEstabelecimentoSaudeDate(idEstabelecimentoSaude, date);
    }
}