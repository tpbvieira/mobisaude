package co.salutary.mobisaude.db;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.db.scripts.ScriptUF;
import co.salutary.mobisaude.model.UF;

public class UfDAO {

    private static final String TAG = UfDAO.class.getSimpleName();

    private LocalDataBase localDB;

    public UfDAO(LocalDataBase db){
        localDB = db;
    }

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
            Log.e(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.e(TAG, "DB Error", e);
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
            Log.e(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.e(TAG, "DB Error", e);
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
            Log.e(TAG, "DB Error", e);
        } catch (Exception e) {
            Log.e(TAG, "DB Error", e);
        }
        return null;
    }

}
