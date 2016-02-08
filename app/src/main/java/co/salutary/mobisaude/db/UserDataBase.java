package co.salutary.mobisaude.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import co.salutary.mobisaude.db.entidade.Cidade;
import co.salutary.mobisaude.db.entidade.Erb;
import co.salutary.mobisaude.db.scipts.DB_SCRIPT;
import co.salutary.mobisaude.db.scipts.ScriptCidade;
import co.salutary.mobisaude.db.scipts.ScriptErb;
import co.salutary.mobisaude.db.scipts.ScriptUF;
import co.salutary.mobisaude.db.entidade.UF;

public class UserDataBase {

    private static final String TAG = UserDataBase.class.getSimpleName();

    protected SQLiteDatabase db;

    private SQLiteHelper dbHelper;

    private static UserDataBase instance = null;

	public static UserDataBase getInstance() {
		if (instance == null) {
			instance = new UserDataBase();
		}
		return instance;
	}

	public void open(Context ctx) {
		// Criar utilizando um script SQL
		dbHelper = new SQLiteHelper(ctx, DB_SCRIPT.NOME_BANCO, DB_SCRIPT.VERSAO_BANCO,
				DB_SCRIPT.SCRIPT_DATABASE_CREATE, DB_SCRIPT.SCRIPT_DATABASE_DELETE);

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

	private long insert(String tabela, ContentValues valores) {
		try {
			long id = db.insert(tabela, "", valores);
			return id;
		} catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
			return 0;
		}catch (Exception e) {
            Log.d(TAG, "DB Error", e);
			return 0;
		}
	}

	private int update(String tabela, ContentValues valores, String where, String[] whereArgs) {
		try {
			int count = db.update(tabela, valores, where, whereArgs);
			return count;
		} catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
			return 0;
		}catch (Exception e) {
            Log.d(TAG, "DB Error", e);
			return 0;
		}
	}

	public int delete(String tabela, String where, String[] whereArgs) {
		try {
			int count = db.delete(tabela, where, whereArgs);
			return count;
		} catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
			return 0;
		}catch (Exception e) {
            Log.d(TAG, "DB Error", e);
			return 0;
		}
	}

	private Cursor getCursor(String tabela, String [] colunas) {
		try {
			return db.query(tabela, colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
            Log.d(TAG, "DB Error", e);
			return null;
		}catch (Exception e) {
            Log.d(TAG, "DB Error", e);
			return null;
		}
	}

	/*** Metodos da tabela Erb */
	public long salvarErb(Erb erb) {
		ContentValues values = getContentValuesErb(erb);
		long id = insert(ScriptErb.NOME_TABELA, values);
		return id;
	}

	public int atualizarErb(Erb erb) {
		ContentValues values = getContentValuesErb(erb);
		values.put(ScriptErb._ID, erb.getId());
		String _id = String.valueOf(erb.getId());
		String where = ScriptErb._ID + "=?";
		String[] whereArgs = new String[] { _id };
		int count = update(ScriptErb.NOME_TABELA, values, where, whereArgs);
		return count;
	}

	public ContentValues getContentValuesErb(Erb erb) {
		ContentValues values = new ContentValues();
		values.put(ScriptErb._ID_CIDADE, erb.getIdCidade());
		values.put(ScriptErb._LATITUDE_STEL, erb.getLatitudeStel());
		values.put(ScriptErb._LONGITUDE_STEL, erb.getLongitudeStel());
		values.put(ScriptErb._NOME_FANTASIA, erb.getNomeFantasia());
		values.put(ScriptErb._PRESTADORA, erb.getPrestadora());
		values.put(ScriptErb._TECNOLOGIA_2G, erb.isTecnologia2g());
		values.put(ScriptErb._TECNOLOGIA_3G, erb.isTecnologia3g());
		values.put(ScriptErb._TECNOLOGIA_4G, erb.isTecnologia4g());
		return values;
	}

	public int deletarErbByMunicipio(long idCidade, String prestadora) {
		String where = 	ScriptErb._ID_CIDADE + "= "+idCidade+"  AND UPPER(" + ScriptErb._PRESTADORA + ") LIKE UPPER('"+prestadora+"') ";
		int count = delete(ScriptErb.NOME_TABELA, where, null);
		return count;
	}

	public Erb getErbById(long id) {
		try {
			Cursor c = db.query(true, ScriptErb.NOME_TABELA, ScriptErb.colunas, ScriptErb._ID + "=" + id, null, null, null, null, null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				int idxId = c.getColumnIndex(ScriptErb._ID);
				int idxIdCidade = c.getColumnIndex(ScriptErb._ID_CIDADE);
				int idxLatitude = c.getColumnIndex(ScriptErb._LATITUDE_STEL);
				int idxLongitude = c.getColumnIndex(ScriptErb._LONGITUDE_STEL);
				int idxNomeFantasia = c.getColumnIndex(ScriptErb._NOME_FANTASIA);
				int idxPrestadora = c.getColumnIndex(ScriptErb._PRESTADORA);
				int idxTecnologia2g = c.getColumnIndex(ScriptErb._TECNOLOGIA_2G);
				int idxTecnologia3g = c.getColumnIndex(ScriptErb._TECNOLOGIA_3G);
				int idxTecnologia4g = c.getColumnIndex(ScriptErb._TECNOLOGIA_4G);

				Erb erb = new Erb();
				erb.setId(c.getLong(idxId));
				erb.setIdCidade(c.getInt(idxIdCidade));
				erb.setLatitudeStel(c.getDouble(idxLatitude));
				erb.setLongitudeStel(c.getDouble(idxLongitude));
				erb.setNomeFantasia(c.getString(idxNomeFantasia));
				erb.setPrestadora(c.getString(idxPrestadora));
				erb.setTecnologia2g(c.getInt(idxTecnologia2g)==1?true:false);
				erb.setTecnologia3g(c.getInt(idxTecnologia3g)==1?true:false);
				erb.setTecnologia4g(c.getInt(idxTecnologia4g)==1?true:false);
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
			String where = ScriptErb._ID_CIDADE + " = " + idCidade;
			if(isShow2g){
				where += " AND " + ScriptErb._TECNOLOGIA_2G + " = 1 ";
			}
			if(isShow3g){
				where += " AND " + ScriptErb._TECNOLOGIA_3G + " = 1 ";
			}
			if(isShow4g){
				where += " AND " + ScriptErb._TECNOLOGIA_4G + " = 1 ";
			}
			if(!listPrestadoras.isEmpty()){
				where += " AND (";
				for (int i = 0; i < listPrestadoras.size(); i++) {
					String prestadora = listPrestadoras.get(i);
					where += "UPPER(" + ScriptErb._PRESTADORA + ")" + " LIKE UPPER('" + prestadora + "')";
					if ((i+1) < listPrestadoras.size()) {
						where += " OR ";
					}
				}
				where += " )";
			}
			Cursor c = db.query(true, ScriptErb.NOME_TABELA, ScriptErb.colunas, where, null, null, null, null, null);
			if (c.moveToFirst()) {
				int idxId = c.getColumnIndex(ScriptErb._ID);
				int idxIdCidade = c.getColumnIndex(ScriptErb._ID_CIDADE);
				int idxLatitude = c.getColumnIndex(ScriptErb._LATITUDE_STEL);
				int idxLongitude = c.getColumnIndex(ScriptErb._LONGITUDE_STEL);
				int idxNomeFantasia = c.getColumnIndex(ScriptErb._NOME_FANTASIA);
				int idxPrestadora = c.getColumnIndex(ScriptErb._PRESTADORA);
				int idxTecnologia2g = c.getColumnIndex(ScriptErb._TECNOLOGIA_2G);
				int idxTecnologia3g = c.getColumnIndex(ScriptErb._TECNOLOGIA_3G);
				int idxTecnologia4g = c.getColumnIndex(ScriptErb._TECNOLOGIA_4G);

				do {
					Erb erb = new Erb();
					erbes.add(erb);

					erb.setId(c.getLong(idxId));
					erb.setIdCidade(c.getInt(idxIdCidade));
					erb.setLatitudeStel(c.getDouble(idxLatitude));
					erb.setLongitudeStel(c.getDouble(idxLongitude));
					erb.setNomeFantasia(c.getString(idxNomeFantasia));
					erb.setPrestadora(c.getString(idxPrestadora));
					erb.setTecnologia2g(c.getInt(idxTecnologia2g)==1?true:false);
					erb.setTecnologia3g(c.getInt(idxTecnologia3g)==1?true:false);
					erb.setTecnologia4g(c.getInt(idxTecnologia4g)==1?true:false);
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

	/*** Metodos da tabela UF and CIDADE */
	public UF getUfById(long id) {
		try {
			Cursor c = db.query(true, ScriptUF.NOME_TABELA, ScriptUF.colunas, ScriptUF._ID_UF + "=" + id, null, null, null, null, null);
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
			Cursor c = getCursor(ScriptUF.NOME_TABELA, ScriptUF.colunas);
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
			Cursor c = db.query(true, ScriptUF.NOME_TABELA, ScriptUF.colunas, where, null, null, null, null, null);
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
			Cursor c = db.query(true, ScriptCidade.NOME_TABELA, ScriptCidade.colunas, ScriptCidade._ID_UF + "=" + idUF, null, null, null, ScriptCidade._NOME, null);
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
			Cursor c = db.query(true, ScriptCidade.NOME_TABELA, ScriptCidade.colunas, ScriptCidade._ID_CIDADE + "=" + id, null, null, null, null, null);
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
			Cursor c = db.query(true, ScriptCidade.NOME_TABELA, ScriptCidade.colunas, where, null, null, null, null, null);
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
			String where =  "UPPER(" + ScriptCidade._NOME + ")" + " LIKE UPPER('" + nome + "') AND " + 
					ScriptCidade._ID_UF + " = " + idUf;
			Cursor c = db.query(true, ScriptCidade.NOME_TABELA, ScriptCidade.colunas, where, null, null, null, null, null);
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

    //lbadias: Available views
    private HashMap<String, Boolean> availableViews;
    public void setAvailableViews(HashMap<String, Boolean> views) {
        availableViews = views;
        availableViews.put("erb", true);

        if(availableViews.size() == 0) {
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
        if(availableViews == null) {
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