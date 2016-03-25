package co.salutary.mobisaude.db;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.db.scripts.ScriptCidade;

/**
 * Created by thiago on 08/02/16.
 */
public class CidadeDAO {

    private static final String TAG = CidadeDAO.class.getSimpleName();

    private LocalDataBase localDB;

    public CidadeDAO(LocalDataBase db){
        localDB = db;
    }

    public List<Cidade> listarCidadesByUF(long idUF) {
        List<Cidade> cidades = new ArrayList<Cidade>();
        try {
            Cursor c = localDB.getDb().query(true, ScriptCidade.NOME_TABELA, ScriptCidade.colunas, ScriptCidade._ID_UF + "=" + idUF, null, null, null, ScriptCidade._NOME, null);
            if (c.moveToFirst()) {
                int idxId = c.getColumnIndex(ScriptCidade._ID_CIDADE);
                int idxIdUf = c.getColumnIndex(ScriptCidade._ID_UF);
                int idxNome = c.getColumnIndex(ScriptCidade._NOME);

                do {
                    Cidade cidade = new Cidade();
                    cidades.add(cidade);

                    cidade.setIdCidade(c.getInt(idxId));
                    cidade.setNome(c.getString(idxNome));
                    cidade.setIdUF(c.getInt(idxIdUf));
                } while (c.moveToNext());
            }

        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
        }
        return cidades;
    }

    public Cidade getCidadeById(long id) {
        try {
            Cursor c = localDB.getDb().query(true, ScriptCidade.NOME_TABELA, ScriptCidade.colunas, ScriptCidade._ID_CIDADE + "=" + id, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                int idxId = c.getColumnIndex(ScriptCidade._ID_CIDADE);
                int idxIdUf = c.getColumnIndex(ScriptCidade._ID_UF);
                int idxNome = c.getColumnIndex(ScriptCidade._NOME);

                Cidade cidade = new Cidade();
                cidade.setIdCidade(c.getInt(idxId));
                cidade.setNome(c.getString(idxNome));
                cidade.setIdUF(c.getInt(idxIdUf));

                return cidade;
            }
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
        }
        return null;
    }

    public Cidade getCidadeByNome(String nome) {
        try {
            String where = "UPPER(" + ScriptCidade._NOME + ")" + " LIKE UPPER('" + nome + "')";
            Cursor c = localDB.getDb().query(true, ScriptCidade.NOME_TABELA, ScriptCidade.colunas, where, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                int idxId = c.getColumnIndex(ScriptCidade._ID_CIDADE);
                int idxIdUf = c.getColumnIndex(ScriptCidade._ID_UF);
                int idxNome = c.getColumnIndex(ScriptCidade._NOME);

                Cidade cidade = new Cidade();
                cidade.setIdCidade(c.getInt(idxId));
                cidade.setNome(c.getString(idxNome));
                cidade.setIdUF(c.getInt(idxIdUf));

                return cidade;
            }
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
        }
        return null;
    }

    public Cidade getCidadeByNomeAndIdUf(String nome, int idUf) {
        try {
            String where = "UPPER(" + ScriptCidade._NOME + ")" + " LIKE UPPER('" + nome + "') AND " +
                    ScriptCidade._ID_UF + " = " + idUf;
            Cursor c = localDB.getDb().query(true, ScriptCidade.NOME_TABELA, ScriptCidade.colunas, where, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                int idxId = c.getColumnIndex(ScriptCidade._ID_CIDADE);
                int idxIdUf = c.getColumnIndex(ScriptCidade._ID_UF);
                int idxNome = c.getColumnIndex(ScriptCidade._NOME);

                Cidade cidade = new Cidade();
                cidade.setIdCidade(c.getInt(idxId));
                cidade.setNome(c.getString(idxNome));
                cidade.setIdUF(c.getInt(idxIdUf));

                return cidade;
            }
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
        }
        return null;
    }

}
