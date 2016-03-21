package co.salutary.mobisaude.db.scipts;

public class ScriptES {

    public static final String NOME_TABELA = "tb_estabelecimento_saude";
    public static final String SCRIPT_DELETAR_TABELA = "DROP TABLE IF EXISTS tb_estabelecimento_saude;";
    public static final String SCRIPT_CRIAR_TABELA =
            "CREATE TABLE tb_estabelecimento_saude ( "
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "idCnes INTEGER, "
            + "idMunicipio INTEGER, "
            + "idTipoEstabelecimentoSaude INTEGER, "
            + "idTipoGestao INTEGER, "
            + "cnpjMantenedora TEXT, "
            + "razaoSocialMantenedora TEXT, "
            + "razaoSocial TEXT, "
            + "nomeFantasia TEXT, "
            + "naturezaOrganizacao TEXT, "
            + "esferaAdministrativa TEXT, "
            + "logradouro TEXT, "
            + "endereco TEXT, "
            + "bairro TEXT, "
            + "cep TEXT, "
            + "origemCoordenada TEXT, "
            + "latitude DOUBLE PRECISION, "
            + "longitude DOUBLE PRECISION, "
            + ");";

    public static final String[] colunas = new String[]{ScriptES._ID,
            ScriptES._ID_CNES,
            ScriptES._ID_MUNICIPIO,
            ScriptES._ID_TIPOESTABELECIMENTOSAUDE,
            ScriptES._ID_TIPOGESTAO,
            ScriptES._CNPJ_MANTENEDORA,
            ScriptES._RAZAO_SOCIAL_MANTENEDORA,
            ScriptES._RAZAO_SOCIAL,
            ScriptES._NOME_FANTASIA,
            ScriptES._NATUREZA_ORGANIZACAO,
            ScriptES._ESFERA_ADMINISTRATIVA,
            ScriptES._LOGRADOURO,
            ScriptES._ENDERECO,
            ScriptES._BAIRRO,
            ScriptES._CEP,
            ScriptES._ORIGEM_COORDENADA,
            ScriptES._LATITUDE,
            ScriptES._LONGITUDE,
    };

    public static final String _ID = "id";
    public static final String _ID_CNES = "idCnes";
    public static final String _ID_MUNICIPIO = "idMunicipio";
    public static final String _ID_TIPOESTABELECIMENTOSAUDE = "idTipoEstabelecimentoSaude";
    public static final String _ID_TIPOGESTAO = "idTipoGestao";
    public static final String _CNPJ_MANTENEDORA = "cnpjMantenedora";
    public static final String _RAZAO_SOCIAL_MANTENEDORA = "razaoSocialMantenedora";
    public static final String _RAZAO_SOCIAL = "razaoSocial";
    public static final String _NOME_FANTASIA = "nomeFantasia";
    public static final String _NATUREZA_ORGANIZACAO = "naturezaOrganizacao";
    public static final String _ESFERA_ADMINISTRATIVA = "esferaAdministrativa";
    public static final String _LOGRADOURO = "logradouro";
    public static final String _ENDERECO = "endereco";
    public static final String _BAIRRO = "bairro";
    public static final String _CEP = "cep";
    public static final String _ORIGEM_COORDENADA = "origemCoordenada";
    public static final String _LATITUDE = "latitude";
    public static final String _LONGITUDE = "longitude";

}