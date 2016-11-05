package co.salutary.mobisaude.model.avaliacaomediames.facade;

import java.util.Date;
import java.util.List;

import co.salutary.mobisaude.model.avaliacaomediames.AvaliacaoMediaMes;

public interface AvaliacaoMediaMesFacade {

	public void save(AvaliacaoMediaMes avaliacaoMedia);
	public void removeByIdESDate(Integer idES, Date date);
	public AvaliacaoMediaMes getByIdESDate(Integer idES, Date date);
	public List<AvaliacaoMediaMes> listByIdES(Integer idES);
	public List<AvaliacaoMediaMes> listByIdESDate(Integer idES, Date date);	
    public List<AvaliacaoMediaMes> listAvaliacaoBySiglaUF(String siglaUF);
    public List<AvaliacaoMediaMes> listAvaliacaoByIdMunicipio(String idMunicipio);
    public List<AvaliacaoMediaMes> listAvaliacaoByIdTipoES(String idTipoEstabelecimento);
    
}