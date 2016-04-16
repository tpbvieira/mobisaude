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

	public Avaliacao getAvaliacao(Integer idEstabelecimentoSaude, String email) {
		return avaliacaoDao.getAvaliacao(idEstabelecimentoSaude, email);
	}

	public void removeAvaliacao(Integer idEstabelecimentoSaude, String email) {
		avaliacaoDao.removeAvaliacao(idEstabelecimentoSaude, email);
	}
	
	public List<Avaliacao> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
    	return avaliacaoDao.listByIdEstabelecimentoSaude(idEstabelecimentoSaude);
    }

	public List<Avaliacao> listByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date) {
    	return avaliacaoDao.listByIdEstabelecimentoSaudeDate(idEstabelecimentoSaude, date);
    }
	
}