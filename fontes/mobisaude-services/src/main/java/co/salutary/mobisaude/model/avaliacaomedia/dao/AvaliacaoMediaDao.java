package co.salutary.mobisaude.model.avaliacaomedia.dao;

import java.util.List;

import co.salutary.mobisaude.model.avaliacaomedia.AvaliacaoMedia;

public interface AvaliacaoMediaDao {

    public void save(AvaliacaoMedia avaliacaoMedi);
    public void removeByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude);
    public AvaliacaoMedia getByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude);    
    public List<AvaliacaoMedia> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude);

}