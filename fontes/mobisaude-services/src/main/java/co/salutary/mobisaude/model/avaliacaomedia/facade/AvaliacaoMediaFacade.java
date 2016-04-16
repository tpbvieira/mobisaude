package co.salutary.mobisaude.model.avaliacaomedia.facade;

import java.util.Date;
import java.util.List;

import co.salutary.mobisaude.model.avaliacaomedia.AvaliacaoMedia;

public interface AvaliacaoMediaFacade {

	public void save(AvaliacaoMedia avaliacaoMedia);
	public void removeByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date);
	public AvaliacaoMedia getByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date);
	public List<AvaliacaoMedia> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude);
	public List<AvaliacaoMedia> listByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date);
	
}