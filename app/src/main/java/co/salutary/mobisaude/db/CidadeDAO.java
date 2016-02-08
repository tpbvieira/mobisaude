package co.salutary.mobisaude.db;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.UF;
import co.salutary.mobisaude.db.scipts.ScriptCidade;
import co.salutary.mobisaude.db.scipts.ScriptUF;

/**
 * Created by thiago on 08/02/16.
 */
public class CidadeDAO {

    private static final String TAG = CidadeDAO.class.getSimpleName();

    private LocalDataBase localDB = LocalDataBase.getInstance();

    /***
     * Metodos da tabela UF and CIDADE
     */
    public UF getUfById(long id) {
        try {
            Cursor c = localDB.getDb().query(true, ScriptUF.NOME_TABELA, ScriptUF.colunas, ScriptUF._ID_UF + "=" + id, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                int idxId = c.getColumnIndex(ScriptUF._ID_UF);
                int idxSgl = c.getColumnIndex(ScriptUF._SIGLA);
                int idxNome = c.getColumnIndex(ScriptUF._NOME);

                UF uf = new UF();
                uf.setIdUf(c.getInt(idxId));
                uf.setSigla(c.getString(idxSgl));
                uf.setNome(c.getString(idxNome));

                return uf;
            }
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
        }
        return null;
    }

    public List<UF> listarUF() {
        List<UF> ufs = new ArrayList<UF>();
        try {
            Cursor c = localDB.getCursor(ScriptUF.NOME_TABELA, ScriptUF.colunas);
            if (c.moveToFirst()) {
                int idxID_UF = c.getColumnIndex(ScriptUF._ID_UF);
                int idxSG_UF = c.getColumnIndex(ScriptUF._SIGLA);
                int idxNM_UF = c.getColumnIndex(ScriptUF._NOME);

                do {
                    UF uf = new UF();
                    ufs.add(uf);

                    uf.setIdUf(c.getInt(idxID_UF));
                    uf.setNome(c.getString(idxNM_UF));
                    uf.setSigla(c.getString(idxSG_UF));

                } while (c.moveToNext());
            }

        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
        }
        return ufs;
    }

    public UF getUfBySigla(String sigla) {
        try {
            String where = "UPPER(" + ScriptUF._SIGLA + ")" + " LIKE UPPER('" + sigla + "')";
            Cursor c = localDB.getDb().query(true, ScriptUF.NOME_TABELA, ScriptUF.colunas, where, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                int idxID_UF = c.getColumnIndex(ScriptUF._ID_UF);
                int idxSG_UF = c.getColumnIndex(ScriptUF._SIGLA);
                int idxNM_UF = c.getColumnIndex(ScriptUF._NOME);

                UF uf = new UF();
                uf.setIdUf(c.getInt(idxID_UF));
                uf.setNome(c.getString(idxNM_UF));
                uf.setSigla(c.getString(idxSG_UF));

                return uf;
            }
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
        }
        return null;
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
