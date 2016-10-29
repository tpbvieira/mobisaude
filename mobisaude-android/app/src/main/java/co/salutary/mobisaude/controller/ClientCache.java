package co.salutary.mobisaude.controller;

import android.content.Context;
import android.util.Log;

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
    private List<EstabelecimentoSaude> listESByCidade;
    private List<EstabelecimentoSaude> listESByTipoES;
    private List<Avaliacao> listAvaliacoes;
    private Avaliacao avaliacaoMedia;

    private ClientCache(){

    }

    private static ClientCache instance = null;

    public static ClientCache getInstance() {
        if (instance == null) {
            instance = new ClientCache();
        }
        return instance;
    }

    public UF getUf() {return uf;}

    public void setUf(UF uf) {this.uf = uf;	}

    public Cidade getCidade() {return cidade;}

    public void setCidade(Cidade cidade) {this.cidade = cidade;}

    public List<EstabelecimentoSaude> getListESByTipoES() {return listESByTipoES;}

    public void setListESByTipoES(List<EstabelecimentoSaude> listESByTipoES) {this.listESByTipoES = listESByTipoES;}

    public List<EstabelecimentoSaude> getListESByCidade() {return listESByCidade;}

    public void setListESByCidade(List<EstabelecimentoSaude> listESByCidade) {this.listESByCidade = listESByCidade;}

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

    public List<String> getPersistentBookmark(Context context){
        List<String> codsES = null;

        try {
            Map<String, String> bestES = null;
            Settings settings = new Settings(context);
            String strBookmark = settings.getPreferenceValue(Settings.BOOKMARK);

            if(strBookmark != null){
                bestES = JsonUtils.fromStrToMap(strBookmark);
            }

            if(bestES == null){
                return null;
            }

            Object[] counts = bestES.entrySet().toArray();
            Arrays.sort(counts, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Map.Entry<String, String>) o2).getValue().compareTo(((Map.Entry<String, String>) o1).getValue());
                }
            });

            codsES = new ArrayList<>();
            for(int i = 0; i<10 && i<counts.length; i++){
                codsES.add(((Map.Entry<String, String>) counts[i]).getKey());
            }
        } catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
        }

        return codsES;

    }

}