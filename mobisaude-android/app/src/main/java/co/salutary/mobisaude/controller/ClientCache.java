package co.salutary.mobisaude.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.model.Avaliacao;
import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.model.UF;
import co.salutary.mobisaude.util.JsonUtils;

public class ClientCache {

    private static final String TAG = new Object(){}.getClass().getName();

    private UF uf;
    private Cidade cidade;
    private EstabelecimentoSaude estabelecimentoSaude;
    private List<EstabelecimentoSaude> listEstabelecimentosSaudeCidade;
    private List<EstabelecimentoSaude> listEstabelecimentosSaudeTipoES;
    private Avaliacao avaliacaoMedia;
    private List<Avaliacao> listAvaliacoes;

    private static ClientCache instance = null;

    public static ClientCache getInstance() {
        if (instance == null) {
            instance = new ClientCache();
        }
        return instance;
    }

    public Cidade getCidade() {return cidade;}

    public void setCidade(Cidade cidade) {this.cidade = cidade;}

    public UF getUf() {return uf;}

    public void setUf(UF uf) {this.uf = uf;	}

    public List<EstabelecimentoSaude> getListEstabelecimentosSaudeCidade() {return listEstabelecimentosSaudeCidade;}

    public void setListEstabelecimentosSaudeCidade(List<EstabelecimentoSaude> listEstabelecimentosSaudeCidade) {this.listEstabelecimentosSaudeCidade = listEstabelecimentosSaudeCidade;}

    public List<EstabelecimentoSaude> getListEstabelecimentosSaudeTipoES() {return listEstabelecimentosSaudeTipoES;}

    public void setListEstabelecimentosSaudeTipoES(List<EstabelecimentoSaude> listEstabelecimentosSaudeTipoES) {this.listEstabelecimentosSaudeTipoES = listEstabelecimentosSaudeTipoES;}

    public EstabelecimentoSaude getEstabelecimentoSaude(){return estabelecimentoSaude;}

    public void setEstabelecimentoSaude(EstabelecimentoSaude es){estabelecimentoSaude = es;}

    public List<Avaliacao> getListAvaliacoes() {return listAvaliacoes;}

    public void setListAvaliacoes(List<Avaliacao> listAvaliacoes) {this.listAvaliacoes = listAvaliacoes;}

    public Avaliacao getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void setAvaliacaoMedia(Avaliacao avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }

    public void countES(Context context, Integer codES){

        Map<String, String> bestES = new HashMap<>();
        Integer count = 1;

        Settings settings = new Settings(context);
        String strBookmark = settings.getPreferenceValue(Settings.BOOKMARK);
        if(strBookmark != null && strBookmark.length() > 2){
            bestES = JsonUtils.fromStrToMap(strBookmark);
            if(bestES.containsKey(codES.toString())){
                count = Integer.valueOf(bestES.get(codES.toString())) + count;
            }
        }

        bestES.put(codES.toString(), count.toString());
        String json = JsonUtils.fromMapToString(bestES);
        settings.setPreferenceValue(Settings.BOOKMARK, json);

    }

    public List<String> getBestES(Context context){

        Map<String, String> bestES = null;

        Settings settings = new Settings(context);
        String strBookmark = settings.getPreferenceValue(Settings.BOOKMARK);
        if(strBookmark != null){
            bestES = JsonUtils.fromStrToMap(strBookmark);
        }

        Object[] counts = bestES.entrySet().toArray();
        List<String> codsES = new ArrayList<>();

        Arrays.sort(counts, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, String>) o2).getValue().compareTo(((Map.Entry<String, String>) o1).getValue());
            }
        });

        for(int i = 0; i<10 && i<counts.length; i++){
            codsES.add(((Map.Entry<String, String>) counts[i]).getKey());
        }
        return codsES;
    }

}