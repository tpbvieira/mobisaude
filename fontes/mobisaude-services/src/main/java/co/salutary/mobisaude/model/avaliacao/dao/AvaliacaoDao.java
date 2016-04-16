package co.salutary.mobisaude.model.avaliacao.dao;

import java.util.Date;
import java.util.List;

import co.salutary.mobisaude.model.avaliacao.Avaliacao;

public interface AvaliacaoDao {

    public void save(Avaliacao avaliacao);
    public void removeByIdEESEmail(Integer idEstabelecimentoSaude, String email);
    public Avaliacao getByIdEESEmail(Integer idEstabelecimentoSaude, String email);
    public Avaliacao getAvgByIdEES(Integer idEstabelecimentoSaude);
    public List<Avaliacao> listByIdES(Integer idEstabelecimentoSaude);
    public List<Avaliacao> listByIdESDate(Integer idEstabelecimentoSaude, Date date);
    
}