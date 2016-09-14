package co.salutary.mobisaude.model.sugestao.dao;

import java.util.List;

import co.salutary.mobisaude.model.sugestao.Sugestao;

public interface SugestaoDao {

    public void save(Sugestao sugestao);    
    public void removeSugestao(Integer idES, String email);
    public Sugestao getSugestao(Integer idES, String email);
    public List<Sugestao> list();
    
}