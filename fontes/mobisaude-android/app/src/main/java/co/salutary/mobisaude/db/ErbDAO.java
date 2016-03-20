package co.salutary.mobisaude.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.model.Erb;
import co.salutary.mobisaude.db.scipts.ScriptES;

/**
 * Created by thiago on 08/02/16.
 */
public class ErbDAO {

    private static final String TAG = ErbDAO.class.getSimpleName();

    private LocalDataBase localDB;

    public ErbDAO(LocalDataBase db){
        localDB = db;
    }

    public long salvarErb(Erb erb) {
        ContentValues values = getContentValuesErb(erb);
        long id = localDB.insert(ScriptES.NOME_TABELA, values);
        return id;
    }

    public int atualizarErb(Erb erb) {
        ContentValues values = getContentValuesErb(erb);
        values.put(ScriptES._ID, erb.getId());
        String _id = String.valueOf(erb.getId());
        String where = ScriptES._ID + "=?";
        String[] whereArgs = new String[]{_id};
        int count = localDB.update(ScriptES.NOME_TABELA, values, where, whereArgs);
        return count;
    }

    public ContentValues getContentValuesErb(Erb erb) {
        ContentValues values = new ContentValues();
        values.put(ScriptES._ID_CIDADE, erb.getIdCidade());
        values.put(ScriptES._LATITUDE_STEL, erb.getLatitudeStel());
        values.put(ScriptES._LONGITUDE_STEL, erb.getLongitudeStel());
        values.put(ScriptES._NOME_FANTASIA, erb.getNomeFantasia());
        values.put(ScriptES._PRESTADORA, erb.getPrestadora());
        values.put(ScriptES._TECNOLOGIA_2G, erb.isTecnologia2g());
        values.put(ScriptES._TECNOLOGIA_3G, erb.isTecnologia3g());
        values.put(ScriptES._TECNOLOGIA_4G, erb.isTecnologia4g());
        return values;
    }

    public int deletarErbByMunicipio(long idCidade, String prestadora) {
        String where = ScriptES._ID_CIDADE + "= " + idCidade + "  AND UPPER(" + ScriptES._PRESTADORA + ") LIKE UPPER('" + prestadora + "') ";
        int count = localDB.delete(ScriptES.NOME_TABELA, where, null);
        return count;
    }

    public Erb getErbById(long id) {
        try {
            Cursor c = localDB.getDb().query(true, ScriptES.NOME_TABELA, ScriptES.colunas, ScriptES._ID + "=" + id, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                int idxId = c.getColumnIndex(ScriptES._ID);
                int idxIdCidade = c.getColumnIndex(ScriptES._ID_CIDADE);
                int idxLatitude = c.getColumnIndex(ScriptES._LATITUDE_STEL);
                int idxLongitude = c.getColumnIndex(ScriptES._LONGITUDE_STEL);
                int idxNomeFantasia = c.getColumnIndex(ScriptES._NOME_FANTASIA);
                int idxPrestadora = c.getColumnIndex(ScriptES._PRESTADORA);
                int idxTecnologia2g = c.getColumnIndex(ScriptES._TECNOLOGIA_2G);
                int idxTecnologia3g = c.getColumnIndex(ScriptES._TECNOLOGIA_3G);
                int idxTecnologia4g = c.getColumnIndex(ScriptES._TECNOLOGIA_4G);

                Erb erb = new Erb();
                erb.setId(c.getLong(idxId));
                erb.setIdCidade(c.getInt(idxIdCidade));
                erb.setLatitudeStel(c.getDouble(idxLatitude));
                erb.setLongitudeStel(c.getDouble(idxLongitude));
                erb.setNomeFantasia(c.getString(idxNomeFantasia));
                erb.setPrestadora(c.getString(idxPrestadora));
                erb.setTecnologia2g(c.getInt(idxTecnologia2g) == 1 ? true : false);
                erb.setTecnologia3g(c.getInt(idxTecnologia3g) == 1 ? true : false);
                erb.setTecnologia4g(c.getInt(idxTecnologia4g) == 1 ? true : false);
                return erb;
            }
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
        }
        return null;
    }

    public List<Erb> listarErb(int idCidade, boolean isShow2g, boolean isShow3g, boolean isShow4g, List<String> listPrestadoras) {
        List<Erb> erbes = new ArrayList<Erb>();
        try {
            String where = ScriptES._ID_CIDADE + " = " + idCidade;
            if (isShow2g) {
                where += " AND " + ScriptES._TECNOLOGIA_2G + " = 1 ";
            }
            if (isShow3g) {
                where += " AND " + ScriptES._TECNOLOGIA_3G + " = 1 ";
            }
            if (isShow4g) {
                where += " AND " + ScriptES._TECNOLOGIA_4G + " = 1 ";
            }
            if (!listPrestadoras.isEmpty()) {
                where += " AND (";
                for (int i = 0; i < listPrestadoras.size(); i++) {
                    String prestadora = listPrestadoras.get(i);
                    where += "UPPER(" + ScriptES._PRESTADORA + ")" + " LIKE UPPER('" + prestadora + "')";
                    if ((i + 1) < listPrestadoras.size()) {
                        where += " OR ";
                    }
                }
                where += " )";
            }
            Cursor c = localDB.getDb().query(true, ScriptES.NOME_TABELA, ScriptES.colunas, where, null, null, null, null, null);
            if (c.moveToFirst()) {
                int idxId = c.getColumnIndex(ScriptES._ID);
                int idxIdCidade = c.getColumnIndex(ScriptES._ID_CIDADE);
                int idxLatitude = c.getColumnIndex(ScriptES._LATITUDE_STEL);
                int idxLongitude = c.getColumnIndex(ScriptES._LONGITUDE_STEL);
                int idxNomeFantasia = c.getColumnIndex(ScriptES._NOME_FANTASIA);
                int idxPrestadora = c.getColumnIndex(ScriptES._PRESTADORA);
                int idxTecnologia2g = c.getColumnIndex(ScriptES._TECNOLOGIA_2G);
                int idxTecnologia3g = c.getColumnIndex(ScriptES._TECNOLOGIA_3G);
                int idxTecnologia4g = c.getColumnIndex(ScriptES._TECNOLOGIA_4G);

                do {
                    Erb erb = new Erb();
                    erbes.add(erb);

                    erb.setId(c.getLong(idxId));
                    erb.setIdCidade(c.getInt(idxIdCidade));
                    erb.setLatitudeStel(c.getDouble(idxLatitude));
                    erb.setLongitudeStel(c.getDouble(idxLongitude));
                    erb.setNomeFantasia(c.getString(idxNomeFantasia));
                    erb.setPrestadora(c.getString(idxPrestadora));
                    erb.setTecnologia2g(c.getInt(idxTecnologia2g) == 1 ? true : false);
                    erb.setTecnologia3g(c.getInt(idxTecnologia3g) == 1 ? true : false);
                    erb.setTecnologia4g(c.getInt(idxTecnologia4g) == 1 ? true : false);
                } while (c.moveToNext());
            }
            return erbes;
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
            return erbes;
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
            return erbes;
        }
    }
}
