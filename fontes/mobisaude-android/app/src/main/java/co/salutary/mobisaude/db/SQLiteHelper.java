package co.salutary.mobisaude.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import co.salutary.mobisaude.db.scripts.ScriptCidade;
import co.salutary.mobisaude.db.scripts.ScriptUF;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getSimpleName();

	private String[] scriptSQLCreate;
	private String[] scriptSQLDelete;

	public SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate, String[] scriptSQLDelete) {
		super(context, nomeBanco, null, versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Criar Tabelas
		int qtdeScriptsCreate = scriptSQLCreate.length;
		for (int i = 0; i < qtdeScriptsCreate; i++) {
			String sql = scriptSQLCreate[i];
			db.execSQL(sql);
		}
		
		// Inserir dados UF
		int qtdeScriptsInsert = ScriptUF.SCRIPT_INSERT_CREATE.length;
		for (int i = 0; i < qtdeScriptsInsert; i++) {
			String sql = ScriptUF.SCRIPT_INSERT_CREATE[i];
			db.execSQL(sql);
		}
		
		// Inserir dados Cidades
		qtdeScriptsInsert = ScriptCidade.SCRIPT_INSERT_CREATE.length;
		for (int i = 0; i < qtdeScriptsInsert; i++) {
			String sql = ScriptCidade.SCRIPT_INSERT_CREATE[i];
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
		// Deleta as tabelas
		int qtdeScripts = scriptSQLDelete.length;
		for (int i = 0; i < qtdeScripts; i++) {
			String sql = scriptSQLDelete[i];
			db.execSQL(sql);
		}
		// Cria novamente...
		onCreate(db);
	}
}