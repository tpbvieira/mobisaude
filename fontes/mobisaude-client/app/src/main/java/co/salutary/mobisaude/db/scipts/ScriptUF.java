package co.salutary.mobisaude.db.scipts;
public class ScriptUF {

	public static final String NOME_TABELA = "uf";
	public static final String SCRIPT_DELETAR_TABELA = "DROP TABLE IF EXISTS uf;";
	public static final String SCRIPT_CRIAR_TABELA = "CREATE TABLE uf ( "
														+ "id_uf INTEGER PRIMARY KEY, "
														+ "sigla TEXT, "
														+ "nome TEXT"
													+ ");";
	
	
	public static final String[] colunas = new String[]{ScriptUF._ID_UF,
														ScriptUF._SIGLA,
														ScriptUF._NOME};

	public static final String _ID_UF = "id_uf";
	public static final String _SIGLA = "sigla";
	public static final String _NOME = "nome";
	
	public static final String[] SCRIPT_INSERT_CREATE = new String[] {
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (12,'Acre','AC');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (27,'Alagoas','AL');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (16,'Amapá','AP');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (13,'Amazonas','AM');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (29,'Bahia','BA');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (23,'Ceará','CE');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (53,'Distrito Federal','DF');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (32,'Espírito Santo','ES');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (52,'Goiás','GO');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (21,'Maranhão','MA');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (51,'Mato Grosso','MT');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (50,'Mato Grosso do Sul','MS');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (31,'Minas Gerais','MG');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (15,'Pará','PA');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (25,'Paraíba','PB');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (41,'Paraná','PR');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (26,'Pernambuco','PE');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (22,'Piauí','PI');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (33,'Rio de Janeiro','RJ');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (24,'Rio Grande do Norte','RN');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (43,'Rio Grande do Sul','RS');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (11,'Rondônia','RO');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (14,'Roraima','RR');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (42,'Santa Catarina','SC');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (35,'São Paulo','SP');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (28,'Sergipe','SE');",
		  "INSERT INTO UF (id_uf, nome, sigla) VALUES (17,'Tocantins','TO');"
	};
}