package co.salutary.mobisaude.model.avaliacao.facade;

import java.util.Date;
import java.util.List;

import co.salutary.mobisaude.model.avaliacao.Avaliacao;

public interface AvaliacaoFacade {

	public void save(Avaliacao avaliacao);
	public void removeAvaliacao(Integer idEstabelecimentoSaude, String email);
	public Avaliacao getAvaliacao(Integer idEstabelecimentoSaude, String email);
	public List<Avaliacao> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude);
	public List<Avaliacao> listByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date);
	
}