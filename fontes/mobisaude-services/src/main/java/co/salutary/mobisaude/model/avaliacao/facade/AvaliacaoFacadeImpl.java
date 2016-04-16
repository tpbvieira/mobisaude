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
	public Avaliacao getByIdEESEmail(Integer idEstabelecimentoSaude, String email) {
		return avaliacaoDao.getByIdEESEmail(idEstabelecimentoSaude, email);
	}
	
	@Override
	public Avaliacao getAvgByIdEES(Integer idEstabelecimentoSaude) {
		return avaliacaoDao.getAvgByIdEES(idEstabelecimentoSaude);
	}

	@Override
	public void removeByIdEESEmail(Integer idEstabelecimentoSaude, String email) {
		avaliacaoDao.removeByIdEESEmail(idEstabelecimentoSaude, email);
	}
	
	@Override
	public List<Avaliacao> listByIdES(Integer idEstabelecimentoSaude) {
    	return avaliacaoDao.listByIdES(idEstabelecimentoSaude);
    }

	@Override
	public List<Avaliacao> listByIdESDate(Integer idEstabelecimentoSaude, Date date) {
    	return avaliacaoDao.listByIdESDate(idEstabelecimentoSaude, date);
    }
	
}