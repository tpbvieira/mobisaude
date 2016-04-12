package co.salutary.mobisaude.model.sugestao.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.sugestao.Sugestao;
import co.salutary.mobisaude.model.sugestao.dao.SugestaoDao;

@Service("sugestaoFacade")
@Transactional(readOnly = true)
public class SugestaoFacadeImpl implements SugestaoFacade {

	@Autowired
	private SugestaoDao sugestaoDao;

	@Transactional(readOnly = false)
	public void save(Sugestao sugestao) {
		sugestaoDao.save(sugestao);
	}

	public Sugestao getSugestao(Integer idEstabelecimentoSaude, String email) {
		return sugestaoDao.getSugestao(idEstabelecimentoSaude, email);
	}

	public void removeSugestao(Integer idEstabelecimentoSaude, String email) {
		sugestaoDao.removeSugestao(idEstabelecimentoSaude, email);
	}
	
}