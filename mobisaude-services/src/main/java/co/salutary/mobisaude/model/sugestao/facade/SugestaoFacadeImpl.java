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

	public Sugestao getSugestao(Integer idES, String email) {
		return sugestaoDao.getSugestao(idES, email);
	}

	public void removeSugestao(Integer idES, String email) {
		sugestaoDao.removeSugestao(idES, email);
	}
	
}