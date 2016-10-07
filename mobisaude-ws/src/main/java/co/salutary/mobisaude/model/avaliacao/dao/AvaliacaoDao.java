package co.salutary.mobisaude.model.avaliacao.dao;

import java.util.Date;
import java.util.List;

import co.salutary.mobisaude.model.avaliacao.Avaliacao;

public interface AvaliacaoDao {

    public void save(Avaliacao avaliacao);
    public void removeByIdESEmail(Integer idES, String email);
    public Avaliacao getByIdESEmail(Integer idES, String email);
    public Avaliacao getAvgByIdES(Integer idES);
    public List<Avaliacao> listByIdES(Integer idES);
    public List<Avaliacao> listByIdESDate(Integer idES, Date date);
    public List<Avaliacao> list();
    
}