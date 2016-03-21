package co.salutary.mobisaude.db.scipts;

public class Scripts {

	public static final String NOME_BANCO = "DB_MOBISAUDE.db";
	public static final int VERSAO_BANCO = 1;

	public static final String[] SCRIPT_DATABASE_CREATE = new String[] {
		ScriptUF.SCRIPT_CRIAR_TABELA,
		ScriptCidade.SCRIPT_CRIAR_TABELA,
		ScriptES.SCRIPT_CRIAR_TABELA,
	};

	public static final String[] SCRIPT_DATABASE_DELETE = new String[] {
		ScriptUF.SCRIPT_DELETAR_TABELA,
		ScriptCidade.SCRIPT_DELETAR_TABELA,
		ScriptES.SCRIPT_DELETAR_TABELA,
	};
}