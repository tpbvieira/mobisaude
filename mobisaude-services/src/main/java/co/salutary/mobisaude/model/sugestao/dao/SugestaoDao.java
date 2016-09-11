package co.salutary.mobisaude.model.sugestao.dao;

import co.salutary.mobisaude.model.sugestao.Sugestao;

public interface SugestaoDao {

    public void save(Sugestao sugestao);

    public Sugestao getSugestao(Integer idES, String email);

    public void removeSugestao(Integer idES, String email);

}