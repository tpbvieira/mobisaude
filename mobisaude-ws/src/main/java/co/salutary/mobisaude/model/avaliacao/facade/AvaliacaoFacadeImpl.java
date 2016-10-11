package co.salutary.mobisaude.model.avaliacao.facade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.avaliacao.Avaliacao;
import co.salutary.mobisaude.model.avaliacao.dao.AvaliacaoDao;

@Service("avaliacaoFacade")
@Transactional(readOnly = true)
public class AvaliacaoFacadeImpl implements AvaliacaoFacade {

	@Autowired
	private AvaliacaoDao avaliacaoDao;

	@Transactional(readOnly = false)
	public void save(Avaliacao avaliacao) {
		avaliacaoDao.save(avaliacao);
	}

	@Override
	public Avaliacao getByIdESEmail(Integer idES, String email) {
		return avaliacaoDao.getByIdESEmail(idES, email);
	}
	
	@Override
	public Avaliacao getAvgByIdES(Integer idES) {
		return avaliacaoDao.getAvgByIdES(idES);
	}

	@Override
	public void removeByIdESEmail(Integer idES, String email) {
		avaliacaoDao.removeByIdESEmail(idES, email);
	}
	
	@Override
	public List<Avaliacao> listByIdES(Integer idES) {
    	return avaliacaoDao.listByIdES(idES);
    }

	@Override
	public List<Avaliacao> listByIdESDate(Integer idES, Date date) {
    	return avaliacaoDao.listByIdESDate(idES, date);
    }

	@Override
	public List<Avaliacao> list() {
		return avaliacaoDao.list();
	}
	
}