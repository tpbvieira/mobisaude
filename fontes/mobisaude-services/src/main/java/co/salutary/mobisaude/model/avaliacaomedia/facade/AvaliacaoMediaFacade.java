package co.salutary.mobisaude.model.avaliacaomedia.facade;

import java.util.List;

import co.salutary.mobisaude.model.avaliacaomedia.AvaliacaoMedia;

public interface AvaliacaoMediaFacade {

	public void save(AvaliacaoMedia avaliacaoMedia);
	public void removeByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude);
	public AvaliacaoMedia getByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude);
	public List<AvaliacaoMedia> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude);
	
}