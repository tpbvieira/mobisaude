package co.salutary.mobisaude.model.avaliacaomediames.dao;

import java.util.Date;
import java.util.List;

import co.salutary.mobisaude.model.avaliacaomediames.AvaliacaoMediaMes;

public interface AvaliacaoMediaMesDao {

    public void save(AvaliacaoMediaMes avaliacaoMedia);
    public void removeByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date);
    public AvaliacaoMediaMes getByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date);
    public List<AvaliacaoMediaMes> listByIdEstabelecimentoSaude(Integer idEstabelecimentoSaude);
    public List<AvaliacaoMediaMes> listByIdEstabelecimentoSaudeDate(Integer idEstabelecimentoSaude, Date date);

}