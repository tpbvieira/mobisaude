package co.salutary.mobisaude.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.db.scripts.ScriptES;
import co.salutary.mobisaude.model.EstabelecimentoSaude;

public class EsDAO {

    private static final String TAG = EsDAO.class.getSimpleName();

    private LocalDataBase localDB;

    public EsDAO(LocalDataBase db) {
        localDB = db;
    }

    public ContentValues getContentValuesES(EstabelecimentoSaude estabelecimentoSaude) {
        ContentValues values = new ContentValues();

        values.put(ScriptES._ID_CNES, estabelecimentoSaude.getIdCnes());
        values.put(ScriptES._ID_MUNICIPIO, estabelecimentoSaude.getIdMunicipio());
        values.put(ScriptES._ID_TIPOESTABELECIMENTOSAUDE, estabelecimentoSaude.getIdTipoEstabelecimentoSaude());
        values.put(ScriptES._ID_TIPOGESTAO, estabelecimentoSaude.getIdTipoGestao());
        values.put(ScriptES._CNPJ_MANTENEDORA, estabelecimentoSaude.getCnpjMantenedora());
        values.put(ScriptES._RAZAO_SOCIAL_MANTENEDORA, estabelecimentoSaude.getRazaoSocialMantenedora());
        values.put(ScriptES._RAZAO_SOCIAL, estabelecimentoSaude.getRazaoSocial());
        values.put(ScriptES._NOME_FANTASIA, estabelecimentoSaude.getNomeFantasia());
        values.put(ScriptES._NATUREZA_ORGANIZACAO, estabelecimentoSaude.getNaturezaOrganizacao());
        values.put(ScriptES._ESFERA_ADMINISTRATIVA, estabelecimentoSaude.getEsferaAdministrativa());
        values.put(ScriptES._LOGRADOURO, estabelecimentoSaude.getLogradouro());
        values.put(ScriptES._ENDERECO, estabelecimentoSaude.getEndereco());
        values.put(ScriptES._BAIRRO, estabelecimentoSaude.getBairro());
        values.put(ScriptES._CEP, estabelecimentoSaude.getCep());
        values.put(ScriptES._ORIGEM_COORDENADA, estabelecimentoSaude.getOrigemCoordenada());
        values.put(ScriptES._LATITUDE, estabelecimentoSaude.getLatitude());
        values.put(ScriptES._LONGITUDE, estabelecimentoSaude.getLongitude());

        return values;
    }

    public EstabelecimentoSaude getESByIdCnes(long idCnes) {
        try {
            Cursor cursor = localDB.getDb().query(true, ScriptES.NOME_TABELA, ScriptES.colunas, ScriptES._ID_CNES + "=" + idCnes, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return EsDAO.fromCursor(cursor);
            }
        } catch (SQLException e) {
            Log.d(TAG, "SQL Error ao obter ES por idCnes", e);
        } catch (Exception e) {
            Log.d(TAG, "Erro inesperado ao obter ES por idCnes", e);
        }
        return null;
    }

    public List<EstabelecimentoSaude> listESByIdMunicipio(int idMunicipio) {
        List<EstabelecimentoSaude> estabelecimentos = new ArrayList<EstabelecimentoSaude>();

        try {
            String where = ScriptES._ID_MUNICIPIO + " = " + idMunicipio;
            Cursor cursor = localDB.getDb().query(true, ScriptES.NOME_TABELA, ScriptES.colunas, where, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    estabelecimentos.add(fromCursor(cursor));
                } while (cursor.moveToNext());
            }

            return estabelecimentos;

        } catch (SQLException e) {
            Log.d(TAG, "Erro SQL ao listar ES por municipio", e);
            return estabelecimentos;
        } catch (Exception e) {
            Log.d(TAG, "Erro inesperado ao listar ES por municipio", e);
            return estabelecimentos;
        }
    }

    public List<EstabelecimentoSaude> listESByIdMynicipioIdTiposEstabelecimento(int idMuicipio, List<String> listTiposES) {
        List<EstabelecimentoSaude> estabelecimentos = new ArrayList<EstabelecimentoSaude>();
        try {
            String where = ScriptES._ID_MUNICIPIO + " = " + idMuicipio;
            if (!listTiposES.isEmpty()) {
                where += " AND (";
                for (int i = 0; i < listTiposES.size(); i++) {
                    String tipoES = listTiposES.get(i);
                    where += "UPPER(" + ScriptES._ID_TIPOESTABELECIMENTOSAUDE + ")" + " LIKE UPPER('" + tipoES + "')";
                    if ((i + 1) < listTiposES.size()) {
                        where += " OR ";
                    }
                }
                where += " )";
            }

            Cursor cursor = localDB.getDb().query(true, ScriptES.NOME_TABELA, ScriptES.colunas, where, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    estabelecimentos.add(fromCursor(cursor));
                } while (cursor.moveToNext());
            }

            return estabelecimentos;

        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
            return estabelecimentos;
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
            return estabelecimentos;
        }
    }

    private static EstabelecimentoSaude fromCursor(Cursor cursor){
        EstabelecimentoSaude estabelecimentoSaude = new EstabelecimentoSaude();
        estabelecimentoSaude.setIdCnes(cursor.getInt(cursor.getColumnIndex(ScriptES._ID_CNES)));
        estabelecimentoSaude.setIdMunicipio(cursor.getInt(cursor.getColumnIndex(ScriptES._ID_MUNICIPIO)));
        estabelecimentoSaude.setIdTipoEstabelecimentoSaude(cursor.getShort(cursor.getColumnIndex(ScriptES._ID_TIPOESTABELECIMENTOSAUDE)));
        estabelecimentoSaude.setIdTipoGestao(cursor.getShort(cursor.getColumnIndex(ScriptES._ID_TIPOGESTAO)));
        estabelecimentoSaude.setCnpjMantenedora(cursor.getString(cursor.getColumnIndex(ScriptES._CNPJ_MANTENEDORA)));
        estabelecimentoSaude.setRazaoSocialMantenedora(cursor.getString(cursor.getColumnIndex(ScriptES._RAZAO_SOCIAL_MANTENEDORA)));
        estabelecimentoSaude.setRazaoSocial(cursor.getString(cursor.getColumnIndex(ScriptES._RAZAO_SOCIAL)));
        estabelecimentoSaude.setNomeFantasia(cursor.getString(cursor.getColumnIndex(ScriptES._NOME_FANTASIA)));
        estabelecimentoSaude.setNaturezaOrganizacao(cursor.getString(cursor.getColumnIndex(ScriptES._NATUREZA_ORGANIZACAO)));
        estabelecimentoSaude.setEsferaAdministrativa(cursor.getString(cursor.getColumnIndex(ScriptES._ESFERA_ADMINISTRATIVA)));
        estabelecimentoSaude.setLogradouro(cursor.getString(cursor.getColumnIndex(ScriptES._LOGRADOURO)));
        estabelecimentoSaude.setEndereco(cursor.getString(cursor.getColumnIndex(ScriptES._ENDERECO)));
        estabelecimentoSaude.setBairro(cursor.getString(cursor.getColumnIndex(ScriptES._BAIRRO)));
        estabelecimentoSaude.setCep(cursor.getString(cursor.getColumnIndex(ScriptES._CEP)));
        estabelecimentoSaude.setOrigemCoordenada(cursor.getString(cursor.getColumnIndex(ScriptES._ORIGEM_COORDENADA)));
        estabelecimentoSaude.setLatitude(cursor.getDouble(cursor.getColumnIndex(ScriptES._LATITUDE)));
        estabelecimentoSaude.setLongitude(cursor.getDouble(cursor.getColumnIndex(ScriptES._LONGITUDE)));
        return estabelecimentoSaude;
    }

}