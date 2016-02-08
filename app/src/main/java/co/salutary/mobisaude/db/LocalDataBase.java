package co.salutary.mobisaude.db;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import co.salutary.mobisaude.db.scipts.Scripts;

public class LocalDataBase {

    private static final String TAG = LocalDataBase.class.getSimpleName();

    protected SQLiteDatabase db;

    private SQLiteHelper dbHelper;

    private static LocalDataBase instance = null;

    public static LocalDataBase getInstance() {
        if (instance == null) {
            instance = new LocalDataBase();
        }
        return instance;
    }

    public void open(Context ctx) {
        // Criar utilizando um script SQL
        dbHelper = new SQLiteHelper(ctx, Scripts.NOME_BANCO, Scripts.VERSAO_BANCO,
                Scripts.SCRIPT_DATABASE_CREATE, Scripts.SCRIPT_DATABASE_DELETE);

        // abre o banco no modo escrita para poder alterar também
        db = dbHelper.getWritableDatabase();
    }

    // Fecha o banco
    public void close() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    protected long insert(String tabela, ContentValues valores) {
        try {
            long id = db.insert(tabela, "", valores);
            return id;
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
            return 0;
        }
    }

    protected int update(String tabela, ContentValues valores, String where, String[] whereArgs) {
        try {
            int count = db.update(tabela, valores, where, whereArgs);
            return count;
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
            return 0;
        }
    }

    protected int delete(String tabela, String where, String[] whereArgs) {
        try {
            int count = db.delete(tabela, where, whereArgs);
            return count;
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
            return 0;
        }
    }

    protected int query(String tabela, String where, String[] whereArgs) {
        try {
            int count = db.delete(tabela, where, whereArgs);
            return count;
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
            return 0;
        }
    }

    protected Cursor getCursor(String tabela, String[] colunas) {
        try {
            return db.query(tabela, colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
            return null;
        } catch (Exception e) {
            Log.d(TAG, "DB Error", e);
            return null;
        }
    }

    protected SQLiteDatabase getDb(){
        return db;
    }

    //lbadias: Available views
    private HashMap<String, Boolean> availableViews;
    public void setAvailableViews(HashMap<String, Boolean> views) {
        availableViews = views;
        availableViews.put("erb", true);

        if (availableViews.size() == 0) {
            //Defaults show all views
            availableViews.put("voz", true);
            availableViews.put("dados", true);
            availableViews.put("dadosGlobal", true);
            availableViews.put("disponibilidade", true);
            availableViews.put("dados2g", true);
            availableViews.put("dados3g", true);
            availableViews.put("dados4g", true);
        }
    }

    public HashMap<String, Boolean> getAvailableViews() {
        if (availableViews == null) {
            availableViews = new HashMap<String, Boolean>();
            availableViews.put("voz", true);
            availableViews.put("dadosGlobal", true);
            availableViews.put("disponibilidade", true);
            availableViews.put("dados2g", true);
            availableViews.put("dados3g", true);
            availableViews.put("dados4g", true);
        }
        return availableViews;
    }
}