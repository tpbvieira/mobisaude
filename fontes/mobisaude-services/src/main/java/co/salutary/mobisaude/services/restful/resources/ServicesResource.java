package co.salutary.mobisaude.services.restful.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import co.salutary.mobisaude.services.model.configuracao.Configuracao;
import co.salutary.mobisaude.services.model.configuracao.facade.ConfiguracaoFacade;
import co.salutary.mobisaude.services.model.estabelecimentosaude.RelatorioErbs;
import co.salutary.mobisaude.services.model.estabelecimentosaude.facade.RelatorioErbsFacade;
import co.salutary.mobisaude.services.model.municipio.facade.MunicipiosIbgeFacade;
import co.salutary.mobisaude.services.model.operadora.facade.OperadoraFacade;
import co.salutary.mobisaude.services.model.regiao.Regiao;
import co.salutary.mobisaude.services.model.regiao.facade.RegiaoFacade;
import co.salutary.mobisaude.services.model.relatorioranking.RelatorioRanking;
import co.salutary.mobisaude.services.model.relatorioranking.facade.RelatorioRankingFacade;
import co.salutary.mobisaude.services.model.tipoestabelecimentosaude.facade.TipoEstabelecimentoSaudeFacade;
import co.salutary.mobisaude.services.model.tipogestao.facade.TipoGestaoFacade;
import co.salutary.mobisaude.services.model.tiposistemaoperacional.facade.TipoSistemaOperacionalFacade;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaDominiosRequest;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaDominiosResponse;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaErbsRequest;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaErbsResponse;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaPatamarRequest;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaPatamarResponse;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaRankingRequest;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaRankingResponse;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaRankingV1Request;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaRankingV1Response;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaRankingV2Request;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaRankingV2Response;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaTelasRequest;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaTelasResponse;
import co.salutary.mobisaude.services.restful.message.mobile.Erb;
import co.salutary.mobisaude.services.restful.message.mobile.GeocodeRequest;
import co.salutary.mobisaude.services.restful.message.mobile.GeocodeResponse;
import co.salutary.mobisaude.services.restful.message.mobile.GerarChaveRequest;
import co.salutary.mobisaude.services.restful.message.mobile.GerarChaveResponse;
import co.salutary.mobisaude.services.restful.message.mobile.GerarTokenRequest;
import co.salutary.mobisaude.services.restful.message.mobile.GerarTokenResponse;
import co.salutary.mobisaude.services.restful.message.mobile.Operadora;
import co.salutary.mobisaude.services.restful.message.mobile.Ranking;
import co.salutary.mobisaude.services.restful.message.mobile.RankingV1;
import co.salutary.mobisaude.services.restful.message.mobile.RankingV2;
import co.salutary.mobisaude.services.restful.message.mobile.RegiaoMsg;
import co.salutary.mobisaude.services.restful.message.mobile.TextoAjudaRequest;
import co.salutary.mobisaude.services.restful.message.mobile.TextoAjudaResponse;
import co.salutary.mobisaude.services.restful.message.mobile.TextoAjudaServicoMovelRequest;
import co.salutary.mobisaude.services.restful.message.mobile.TextoAjudaServicoMovelResponse;
import co.salutary.mobisaude.services.restful.message.mobile.TextoAvisoRelatarProblemaRequest;
import co.salutary.mobisaude.services.restful.message.mobile.TextoAvisoRelatarProblemaResponse;
import co.salutary.mobisaude.services.restful.message.mobile.TipoEstabelecimentoSaudeMsg;
import co.salutary.mobisaude.services.restful.message.mobile.TipoGestaoMsg;
import co.salutary.mobisaude.services.restful.message.mobile.TipoSistemaOperacional;
import co.salutary.util.Factory;

/**
 *	Resource para os servicos 
 *
 */
@Path("/mobile")
@Controller
public class ServicesResource extends AbstractServicesMobileResource {
	/**
	 * Logger
	 */
	private static final Log logger = LogFactory.getLog(ServicesResource.class);

	/**
	 * Chave para configuracao Texto Ajuda Mapa Publico
	 */
	public static final String TEXTO_AJUDA_MAPA_PUBLICO = "TEXTO_AJUDA_MAPA_PUBLICO";
	/**
	 * Chave para configuracao Texto Ajuda Servico Movel
	 */
	public static final String TEXTO_AJUDA_SERVICO_MOVEL = "TEXTO_AJUDA_SERVICO_MOVEL";
	/**
	 * Chave para configuracao Texto Aviso do Relatar Problema
	 */
	public static final String TEXTO_AVISO_RELATAR_PROBLEMAS = "TEXTO_AVISO_RELATAR_PROBLEMAS";
	/**
	 * Chave para configuracao Exibir Ranking de Disponibilidade
	 */
	public static final String EXIBIR_RANKING_DISPONIBILIDADE = "EXIBIR_RANKING_DISPONIBILIDADE";
	/**
	 * Chave para configuracao Exibir Ranking de Voz
	 */
	public static final String EXIBIR_RANKING_VOZ = "EXIBIR_RANKING_VOZ";
	/**
	 * Chave para configuracao Exibir Ranking de Dados
	 */
	public static final String EXIBIR_RANKING_DADOS = "EXIBIR_RANKING_DADOS";
	/**
	 * Chave para configuracao Exibir Ranking de Dados 2G
	 */
	public static final String EXIBIR_RANKING_DADOS_2G = "EXIBIR_RANKING_DADOS_2G";
	/**
	 * Chave para configuracao Exibir Ranking de Dados 3G
	 */
	public static final String EXIBIR_RANKING_DADOS_3G = "EXIBIR_RANKING_DADOS_3G";
	/**
	 * Chave para configuracao Exibir Ranking de Dados 4G
	 */
	public static final String EXIBIR_RANKING_DADOS_4G = "EXIBIR_RANKING_DADOS_4G";
	/**
	 * Chave para configuracao Exibir Ranking de Dados Global
	 */
	public static final String EXIBIR_RANKING_DADOS_GLOBAL = "EXIBIR_RANKING_DADOS_GLOBAL";

	/**
	 * Construtor
	 */
	public ServicesResource() {

	}
	
	/**
	 * Metodo que trata o request de geocode: determinar o municipio dadas as coordenadas.
	 * @param request
	 * @return
	 */
	@POST
	@Path("/geocode")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GeocodeResponse geocode(GeocodeRequest request) {
		GeocodeResponse response = new GeocodeResponse();
		try {
			if (!request.validar()) {
				response.setErro(properties.getProperty("gateway.mobile.geocode.msg.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Geocode.");
				return response;
			}

			String token = request.getToken();

			if (!validarToken(token)) {
				logger.error("Sessão inválida no serviço Geocode.");
				response.setErro(properties.getProperty("gateway.mobile.geocode.msg.tokenInvalido"));
				return response;
			}

			String sLatitude = request.getLatitude();
			String sLongitude = request.getLongitude();
			Double latitude = null;
			Double longitude = null;
			try {
				latitude = Double.parseDouble(sLatitude);
			} catch (Exception ex) {
				response.setErro(properties.getProperty("gateway.mobile.geocode.msg.latitudeInvalida"));
				logger.error("Latitude inválida no serviço Mobile Geocode.");
				return response;
			}
			try {
				longitude = Double.parseDouble(sLongitude);
			} catch (Exception ex) {
				response.setErro(properties.getProperty("gateway.mobile.geocode.msg.longitudeInvalida"));
				logger.error("Longitude inválida no serviço Mobile Geocode.");
				return response;
			}

			MunicipiosIbgeFacade municipiosIbgeFacade = (MunicipiosIbgeFacade)Factory.getInstance().get("municipiosIbgeFacade");
			String[] dadosMunicipio = municipiosIbgeFacade.getCodMunicipioByCoord(latitude, longitude);
			if (dadosMunicipio != null) {
				response.setCodMunicipioIbge(dadosMunicipio[0]);
				response.setMunicipio(dadosMunicipio[1]);
				response.setUf(dadosMunicipio[2]);
				response.setErro(properties.getProperty("gateway.mobile.geocode.msg.sucesso"));
			} else {
				response.setErro(properties.getProperty("gateway.mobile.geocode.msg.localidadeNaoEncontrada"));
				logger.warn("Município não encontrado no serviço Mobile Geocode.");
			}
			//response.setErro(properties.getProperty("gateway.mobile.geocode.msg.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.geocode.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Geocode.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Metodo que trata o request de geracao de token de sessao.
	 * @param request
	 * @return
	 */
	@POST
	@Path("/gerarToken")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GerarTokenResponse gerarToken(GerarTokenRequest request) {
		GerarTokenResponse response = new GerarTokenResponse();
		try {
			if (!request.validar()) {
				response.setErro(properties.getProperty("gateway.mobile.gerartoken.msg.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Gerar Token.");
				return response;
			}

			String chave = request.getChave();

			String token = gerarToken(chave);

			if (token != null) {
				response.setToken(token);
				response.setErro(properties.getProperty("gateway.mobile.gerartoken.msg.sucesso"));
			} else {
				response.setErro(properties.getProperty("gateway.mobile.gerartoken.msg.erroGerandoToken"));
				return response;
			}
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.gerartoken.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Gerar Token.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Metodo que trata o request de geracao de chave para geracao de token.
	 * @param request
	 * @return
	 */
	@POST
	@Path("/gerarChave")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GerarChaveResponse gerarChave(GerarChaveRequest request) {
		GerarChaveResponse response = new GerarChaveResponse();
		try {
			if (!request.validar()) {
				response.setErro(properties.getProperty("gateway.mobile.gerarchave.msg.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Gerar Chave.");
				return response;
			}

			String chave = gerarChave();

			if (chave != null) {
				response.setChave(chave);
				response.setErro(properties.getProperty("gateway.mobile.gerarchave.msg.sucesso"));
			} else {
				response.setErro(properties.getProperty("gateway.mobile.gerarchave.msg.erroGerandoChave"));
				return response;
			}
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.gerartoken.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Gerar Token.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Metodo que trata o request de consulta de ERBs por municipio 
	 * @param request
	 * @return
	 */
	@POST
	@Path("/consultaErbsPorMunicipio")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ConsultaErbsResponse consultaErbsPorMunicipio(ConsultaErbsRequest request) {
		ConsultaErbsResponse response = new ConsultaErbsResponse();
		try {
			if (!request.validar()) {
				response.setErro(properties.getProperty("gateway.mobile.consultaerbsmunicipio.msg.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Consulta ERBs por municipio.");
				return response;
			}

			String token = request.getToken();

			if (!validarToken(token)) {
				logger.error("Sessão inválida no serviço Consulta ERBs.");
				response.setErro(properties.getProperty("gateway.mobile.consultaerbsmunicipio.msg.tokenInvalido"));
				return response;
			}

			String uf = request.getUf();
			String municipio = request.getMunicipio();
			String operadora = request.getOperadora();
			String[] operadoras = request.getOperadoras();

			if (municipio == null || municipio.trim().equals("")) {
				response.setErro(properties.getProperty("gateway.mobile.consultaerbsmunicipio.msg.municipioInvalido"));
				logger.error("Municipio inválido no serviço Mobile Consulta ERBs por municipio.");
				return response;
			}
			if (uf == null || uf.trim().equals("")) {
				response.setErro(properties.getProperty("gateway.mobile.consultaerbsmunicipio.msg.ufInvalida"));
				logger.error("UF inválida no serviço Mobile Consulta ERBs por municipio.");
				return response;
			}

			RelatorioErbsFacade relatorioErbsFacade = (RelatorioErbsFacade)Factory.getInstance().get("relatorioErbsFacade");
			List<RelatorioErbs> lstRelatorioErbs = null;
			if (operadoras != null) {
				if (operadoras.length > 0) {
					lstRelatorioErbs = relatorioErbsFacade.listByUfMunicipioOperadoras(uf, municipio, operadoras);
				} else {
					lstRelatorioErbs = new ArrayList<RelatorioErbs>();
				}
			} else {
				if (operadora != null) {
					lstRelatorioErbs = relatorioErbsFacade.listByUfMunicipioOperadora(uf, municipio, operadora);
				} else {
					lstRelatorioErbs = new ArrayList<RelatorioErbs>();
				}
			}
			if (lstRelatorioErbs != null) {
				List<Erb> lstErbs = new ArrayList<Erb>();
				for (RelatorioErbs erb:lstRelatorioErbs) {
					Erb objErb = new Erb();
					objErb.setLatitudeStel(String.valueOf(erb.getLatitudeStel()));
					objErb.setLongitudeStel(String.valueOf(erb.getLongitudeStel()));
					objErb.setUf(erb.getUf());
					objErb.setCodMunicipioIbge(erb.getCodMunicipioIbge());
					objErb.setMunicipio(erb.getMunicipio());
					objErb.setNomeFantasia(erb.getPrestadora()); //
					objErb.setPrestadora(erb.getPrestadora());
					objErb.setTecnologia2g(String.valueOf(erb.getTecnologia2g()));
					objErb.setTecnologia3g(String.valueOf(erb.getTecnologia3g()));
					objErb.setTecnologia4g(String.valueOf(erb.getTecnologia4g()));

					lstErbs.add(objErb);
				}
				response.setErbs(lstErbs.toArray(new Erb[0]));
			} else {
				logger.warn("ERBs não encontrados no serviço Mobile Consulta ERBs por municipio.");
			}
			response.setErro(properties.getProperty("gateway.mobile.consultaerbsmunicipio.msg.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.consultaerbsmunicipio.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Consulta ERBs por municipio.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Metodo que trata o request de consulta de Ranking por municipio 
	 * @param request
	 * @return
	 */
	@POST
	@Path("/consultaRankingPorMunicipio")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ConsultaRankingResponse consultaRankingPorMunicipio(ConsultaRankingRequest request) {
		ConsultaRankingResponse response = new ConsultaRankingResponse();
		try {
			if (!request.validar()) {
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipio.msg.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Consulta Ranking por municipio.");
				return response;
			}

			String token = request.getToken();

			if (!validarToken(token)) {
				logger.error("Sessão inválida no serviço Consulta Ranking.");
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipio.msg.tokenInvalido"));
				return response;
			}

			String uf = request.getUf();
			String municipio = request.getMunicipio();
			if (municipio == null || municipio.trim().equals("")) {
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipio.msg.municipioInvalido"));
				logger.error("Municipio inválido no serviço Mobile Consulta Ranking por municipio.");
				return response;
			}
			if (uf == null || uf.trim().equals("")) {
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipio.msg.ufInvalida"));
				logger.error("UF inválida no serviço Mobile Consulta Ranking por municipio.");
				return response;
			}

			RelatorioRankingFacade relatorioRankingFacade = (RelatorioRankingFacade)Factory.getInstance().get("relatorioRankingFacade");
			List<RelatorioRanking> lstRelatorioRanking = relatorioRankingFacade.listByUfMunicipio(uf, municipio);
			if (lstRelatorioRanking != null) {
				List<Ranking> lstRanking = new ArrayList<Ranking>();
				for (RelatorioRanking ranking:lstRelatorioRanking) {
					Ranking objRanking = new Ranking();
					objRanking.setCodMunicipioIbge(ranking.getCodMunicipioIbge());
					objRanking.setMunicipio(ranking.getMunicipio());
					objRanking.setUf(ranking.getUf());
					objRanking.setPrestadora(ranking.getPrestadora());
					objRanking.setNomeFantasia(ranking.getPrestadora()); //
					objRanking.setQtdTecnologia2g(String.valueOf(ranking.getQtdTecnologia2g()));
					objRanking.setQtdTecnologia3g(String.valueOf(ranking.getQtdTecnologia3g()));
					objRanking.setQtdTecnologia4g(String.valueOf(ranking.getQtdTecnologia4g()));
					objRanking.setConexaoDados(ranking.getConexaoDados() != null ? String.valueOf(ranking.getConexaoDados()) : "0");
					objRanking.setConexaoVoz(ranking.getConexaoVoz() != null ? String.valueOf(ranking.getConexaoVoz()) : "0");
					objRanking.setDesconexaoDados(ranking.getDesconexaoDados() != null ? String.valueOf(ranking.getDesconexaoDados()) : "0");
					objRanking.setDesconexaoVoz(ranking.getDesconexaoVoz() != null ? String.valueOf(ranking.getDesconexaoVoz()) : "0");
					objRanking.setRankingDados(String.valueOf(ranking.getRankingDados()));
					objRanking.setRankingVoz(String.valueOf(ranking.getRankingVoz()));

					lstRanking.add(objRanking);
				}
				response.setRankings(lstRanking.toArray(new Ranking[0]));
			} else {
				logger.warn("ERBs não encontrados no serviço Mobile Consulta Ranking por  municipio.");
			}
			response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipio.msg.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipio.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Consulta Ranking por municipio.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Metodo que trata o request de consulta de Ranking por municipio 
	 * Nova versao v1
	 * @param request
	 * @return
	 */
	@POST
	@Path("/consultaRankingPorMunicipioV1")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ConsultaRankingV1Response consultaRankingPorMunicipioV1(ConsultaRankingV1Request request) {
		ConsultaRankingV1Response response = new ConsultaRankingV1Response();
		try {
			if (!request.validar()) {
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov1.msg.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Consulta Ranking por municipio v1.");
				return response;
			}

			String token = request.getToken();

			if (!validarToken(token)) {
				logger.error("Sessão inválida no serviço Consulta Ranking v1.");
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov1.msg.tokenInvalido"));
				return response;
			}

			String uf = request.getUf();
			String municipio = request.getMunicipio();
			if (municipio == null || municipio.trim().equals("")) {
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov1.msg.municipioInvalido"));
				logger.error("Municipio inválido no serviço Mobile Consulta Ranking por municipio v1.");
				return response;
			}
			if (uf == null || uf.trim().equals("")) {
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov1.msg.ufInvalida"));
				logger.error("UF inválida no serviço Mobile Consulta Ranking por municipio v1.");
				return response;
			}

			RelatorioRankingFacade relatorioRankingFacade = (RelatorioRankingFacade)Factory.getInstance().get("relatorioRankingFacade");
			List<RelatorioRanking> lstRelatorioRanking = relatorioRankingFacade.listByUfMunicipio(uf, municipio);
			if (lstRelatorioRanking != null) {
				List<RankingV1> lstRanking = new ArrayList<RankingV1>();
				for (RelatorioRanking ranking:lstRelatorioRanking) {
					RankingV1 objRanking = new RankingV1();
					objRanking.setCodMunicipioIbge(ranking.getCodMunicipioIbge());
					objRanking.setMunicipio(ranking.getMunicipio());
					objRanking.setUf(ranking.getUf());
					objRanking.setPrestadora(ranking.getPrestadora());
					objRanking.setNomeFantasia(ranking.getPrestadora()); //
					objRanking.setQtdTecnologia2g(String.valueOf(ranking.getQtdTecnologia2g()));
					objRanking.setQtdTecnologia3g(String.valueOf(ranking.getQtdTecnologia3g()));
					objRanking.setQtdTecnologia4g(String.valueOf(ranking.getQtdTecnologia4g()));
					objRanking.setConexaoDados(ranking.getConexaoDados() != null ? String.valueOf(ranking.getConexaoDados()) : "0");
					objRanking.setConexaoVoz(ranking.getConexaoVoz() != null ? String.valueOf(ranking.getConexaoVoz()) : "0");
					objRanking.setDesconexaoDados(ranking.getDesconexaoDados() != null ? String.valueOf(ranking.getDesconexaoDados()) : "0");
					objRanking.setDesconexaoVoz(ranking.getDesconexaoVoz() != null ? String.valueOf(ranking.getDesconexaoVoz()) : "0");
					objRanking.setRankingDados(String.valueOf(ranking.getRankingDados()));
					objRanking.setRankingVoz(String.valueOf(ranking.getRankingVoz()));
					objRanking.setConexaoDados2g(ranking.getConexaoDados2g() != null ? String.valueOf(ranking.getConexaoDados2g()) : "0");
					objRanking.setDesconexaoDados2g(ranking.getDesconexaoDados2g() != null ? String.valueOf(ranking.getDesconexaoDados2g()) : "0");
					objRanking.setConexaoDados3g(ranking.getConexaoDados3g() != null ? String.valueOf(ranking.getConexaoDados3g()) : "0");
					objRanking.setDesconexaoDados3g(ranking.getDesconexaoDados3g() != null ? String.valueOf(ranking.getDesconexaoDados3g()) : "0");
					objRanking.setConexaoDados4g(ranking.getConexaoDados4g() != null ? String.valueOf(ranking.getConexaoDados4g()) : "0");
					objRanking.setDesconexaoDados4g(ranking.getDesconexaoDados4g() != null ? String.valueOf(ranking.getDesconexaoDados4g()) : "0");
					objRanking.setRankingDados2g(String.valueOf(ranking.getRankingDados2g()));
					objRanking.setRankingDados3g(String.valueOf(ranking.getRankingDados3g()));
					objRanking.setRankingDados4g(String.valueOf(ranking.getRankingDados4g()));
					objRanking.setIndiceVoz(ranking.getIndiceVoz() != null ? String.valueOf(ranking.getIndiceVoz()) : "0");
					objRanking.setIndiceDados(ranking.getIndiceDados() != null ? String.valueOf(ranking.getIndiceDados()) : "0");
					objRanking.setIndiceDados2g(ranking.getIndiceDados2g() != null ? String.valueOf(ranking.getIndiceDados2g()) : "0");
					objRanking.setIndiceDados3g(ranking.getIndiceDados3g() != null ? String.valueOf(ranking.getIndiceDados3g()) : "0");
					objRanking.setIndiceDados4g(ranking.getIndiceDados4g() != null ? String.valueOf(ranking.getIndiceDados4g()) : "0");

					lstRanking.add(objRanking);
				}
				response.setRankings(lstRanking.toArray(new RankingV1[0]));
			} else {
				logger.warn("ERBs não encontrados no serviço Mobile Consulta Ranking por  municipio v1.");
			}
			response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov1.msg.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov1.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Consulta Ranking por municipio v1.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Metodo que trata o request de consulta de Ranking por municipio 
	 * Nova versao v2
	 * @param request
	 * @return
	 */
	@POST
	@Path("/consultaRankingPorMunicipioV2")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ConsultaRankingV2Response consultaRankingPorMunicipioV2(ConsultaRankingV2Request request) {
		ConsultaRankingV2Response response = new ConsultaRankingV2Response();
		try {
			if (!request.validar()) {
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov2.msg.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Consulta Ranking por municipio v2.");
				return response;
			}

			String token = request.getToken();

			if (!validarToken(token)) {
				logger.error("Sessão inválida no serviço Consulta Ranking v2.");
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov2.msg.tokenInvalido"));
				return response;
			}

			String uf = request.getUf();
			String municipio = request.getMunicipio();
			if (municipio == null || municipio.trim().equals("")) {
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov2.msg.municipioInvalido"));
				logger.error("Municipio inválido no serviço Mobile Consulta Ranking por municipio v2.");
				return response;
			}
			if (uf == null || uf.trim().equals("")) {
				response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov2.msg.ufInvalida"));
				logger.error("UF inválida no serviço Mobile Consulta Ranking por municipio v2.");
				return response;
			}

			RelatorioRankingFacade relatorioRankingFacade = (RelatorioRankingFacade)Factory.getInstance().get("relatorioRankingFacade");
			List<RelatorioRanking> lstRelatorioRanking = relatorioRankingFacade.listByUfMunicipio(uf, municipio);
			if (lstRelatorioRanking != null) {
				List<RankingV2> lstRanking = new ArrayList<RankingV2>();
				for (RelatorioRanking ranking:lstRelatorioRanking) {
					RankingV2 objRanking = new RankingV2();
					objRanking.setCodMunicipioIbge(ranking.getCodMunicipioIbge());
					objRanking.setMunicipio(ranking.getMunicipio());
					objRanking.setUf(ranking.getUf());
					objRanking.setPrestadora(ranking.getPrestadora());
					objRanking.setNomeFantasia(ranking.getPrestadora()); //
					objRanking.setQtdTecnologia2g(String.valueOf(ranking.getQtdTecnologia2g()));
					objRanking.setQtdTecnologia3g(String.valueOf(ranking.getQtdTecnologia3g()));
					objRanking.setQtdTecnologia4g(String.valueOf(ranking.getQtdTecnologia4g()));
					objRanking.setConexaoDados(ranking.getConexaoDados() != null ? String.valueOf(ranking.getConexaoDados()) : "0");
					objRanking.setConexaoVoz(ranking.getConexaoVoz() != null ? String.valueOf(ranking.getConexaoVoz()) : "0");
					objRanking.setDesconexaoDados(ranking.getDesconexaoDados() != null ? String.valueOf(ranking.getDesconexaoDados()) : "0");
					objRanking.setDesconexaoVoz(ranking.getDesconexaoVoz() != null ? String.valueOf(ranking.getDesconexaoVoz()) : "0");
					objRanking.setRankingDados(String.valueOf(ranking.getRankingDados()));
					objRanking.setRankingVoz(String.valueOf(ranking.getRankingVoz()));
					objRanking.setConexaoDados2g(ranking.getConexaoDados2g() != null ? String.valueOf(ranking.getConexaoDados2g()) : "0");
					objRanking.setDesconexaoDados2g(ranking.getDesconexaoDados2g() != null ? String.valueOf(ranking.getDesconexaoDados2g()) : "0");
					objRanking.setConexaoDados3g(ranking.getConexaoDados3g() != null ? String.valueOf(ranking.getConexaoDados3g()) : "0");
					objRanking.setDesconexaoDados3g(ranking.getDesconexaoDados3g() != null ? String.valueOf(ranking.getDesconexaoDados3g()) : "0");
					objRanking.setConexaoDados4g(ranking.getConexaoDados4g() != null ? String.valueOf(ranking.getConexaoDados4g()) : "0");
					objRanking.setDesconexaoDados4g(ranking.getDesconexaoDados4g() != null ? String.valueOf(ranking.getDesconexaoDados4g()) : "0");
					objRanking.setRankingDados2g(String.valueOf(ranking.getRankingDados2g()));
					objRanking.setRankingDados3g(String.valueOf(ranking.getRankingDados3g()));
					objRanking.setRankingDados4g(String.valueOf(ranking.getRankingDados4g()));
					objRanking.setIndiceVoz(ranking.getIndiceVoz() != null ? String.valueOf(ranking.getIndiceVoz()) : "0");
					objRanking.setIndiceDados(ranking.getIndiceDados() != null ? String.valueOf(ranking.getIndiceDados()) : "0");
					objRanking.setIndiceDados2g(ranking.getIndiceDados2g() != null ? String.valueOf(ranking.getIndiceDados2g()) : "0");
					objRanking.setIndiceDados3g(ranking.getIndiceDados3g() != null ? String.valueOf(ranking.getIndiceDados3g()) : "0");
					objRanking.setIndiceDados4g(ranking.getIndiceDados4g() != null ? String.valueOf(ranking.getIndiceDados4g()) : "0");
					objRanking.setDisponibilidade(ranking.getDisponibilidade() != null ? String.valueOf(ranking.getDisponibilidade()) : "0");

					lstRanking.add(objRanking);
				}
				response.setRankings(lstRanking.toArray(new RankingV2[0]));
			} else {
				logger.warn("ERBs não encontrados no serviço Mobile Consulta Ranking por  municipio v2.");
			}
			response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov2.msg.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.consultarankingmunicipiov2.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Consulta Ranking por municipio v2.", ex);
			return response;
		}
		return response;
	}			

	private Collection listarTiposExibir() {
		Collection retorno = new ArrayList<String>();

		ConfiguracaoFacade configuracaoFacade = (ConfiguracaoFacade)Factory.getInstance().get("configuracaoFacade");

		Configuracao configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DISPONIBILIDADE);
		if (configuracao != null) {
			if (configuracao.getValor().equals("S")) {
				retorno.add("DISPONIBILIDADE");
			}
		}
		configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS);
		if (configuracao != null) {
			if (configuracao.getValor().equals("S")) {
				retorno.add("DADOS");
			}
		}
		configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_2G);
		if (configuracao != null) {
			if (configuracao.getValor().equals("S")) {
				retorno.add("DADOS 2G");
			}
		}
		configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_3G);
		if (configuracao != null) {
			if (configuracao.getValor().equals("S")) {
				retorno.add("DADOS 3G");
			}
		}
		configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_4G);
		if (configuracao != null) {
			if (configuracao.getValor().equals("S")) {
				retorno.add("DADOS 4G");
			}
		}
		configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_VOZ);
		if (configuracao != null) {
			if (configuracao.getValor().equals("S")) {
				retorno.add("VOZ");
			}
		}

		return retorno;
	}

	/**
	 * Metodo que trata o request de obter texto de ajuda
	 * @param request
	 * @return
	 */
	@POST
	@Path("/textoAjuda")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public TextoAjudaResponse textoAjuda(TextoAjudaRequest request) {
		TextoAjudaResponse response = new TextoAjudaResponse();

		try {
			ConfiguracaoFacade configuracaoFacade = (ConfiguracaoFacade)Factory.getInstance().get("configuracaoFacade");
			Configuracao configuracao = configuracaoFacade.getConfiguracao(TEXTO_AJUDA_MAPA_PUBLICO);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setTexto(configuracao.getValor());
					response.setErro(properties.getProperty("gateway.mobile.textoAjuda.msg.sucesso"));
				} else {
					response.setErro(properties.getProperty("gateway.mobile.textoAjuda.msg.erroTextoAjuda"));
					logger.error("Erro obtendo texto da ajuda no serviço Mobile Texto Ajuda.");
				}
			}
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.textoAjuda.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Texto Ajuda.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Metodo que trata o request de obter texto de ajuda do servico movel
	 * @param request
	 * @return
	 */
	@POST
	@Path("/textoAjudaServicoMovel")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public TextoAjudaServicoMovelResponse textoAjudaServicoMovel(TextoAjudaServicoMovelRequest request) {
		TextoAjudaServicoMovelResponse response = new TextoAjudaServicoMovelResponse();

		try {
			ConfiguracaoFacade configuracaoFacade = (ConfiguracaoFacade)Factory.getInstance().get("configuracaoFacade");
			Configuracao configuracao = configuracaoFacade.getConfiguracao(TEXTO_AJUDA_SERVICO_MOVEL);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setTexto(configuracao.getValor());
					response.setErro(properties.getProperty("gateway.mobile.textoAjudaServicoMovel.msg.sucesso"));
				} else {
					response.setErro(properties.getProperty("gateway.mobile.textoAjudaServicoMovel.msg.erroTextoAjuda"));
					logger.error("Erro obtendo texto da ajuda no serviço Mobile Texto Ajuda Servico Movel.");
				}
			}
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.textoAjudaServicoMovel.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Texto Ajuda Servico Movel.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Metodo que trata o request de obter texto do aviso do relatar problema
	 * @param request
	 * @return
	 */
	@POST
	@Path("/textoAvisoRelatarProblema")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public TextoAvisoRelatarProblemaResponse textoAvisoRelatarProblema(TextoAvisoRelatarProblemaRequest request) {
		TextoAvisoRelatarProblemaResponse response = new TextoAvisoRelatarProblemaResponse();

		try {
			ConfiguracaoFacade configuracaoFacade = (ConfiguracaoFacade)Factory.getInstance().get("configuracaoFacade");
			Configuracao configuracao = configuracaoFacade.getConfiguracao(TEXTO_AVISO_RELATAR_PROBLEMAS);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setTexto(configuracao.getValor());
					response.setErro(properties.getProperty("gateway.mobile.textoAvisoRelatarProblema.msg.sucesso"));
				} else {
					response.setErro(properties.getProperty("gateway.mobile.textoAvisoRelatarProblema.msg.erroTextoAvisoRelatarProblema"));
					logger.error("Erro obtendo texto da ajuda no serviço Mobile Texto Aviso Relatar Problema.");
				}
			}
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.textoAvisoRelatarProblema.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Texto Aviso Relatar Problema.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Metodo que trata o request de obter texto de ajuda
	 * @param request
	 * @return
	 */
	@POST
	@Path("/consultaPatamar")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ConsultaPatamarResponse consultaPatamar(ConsultaPatamarRequest request) {
		ConsultaPatamarResponse response = new ConsultaPatamarResponse();

		try {
			String patamarAcessoVozMin = "";
			String patamarAcessoVozMax = "";
			String patamarQuedaVozMin = "";
			String patamarQuedaVozMax = "";
			String patamarAcessoDadosMin = "";
			String patamarAcessoDadosMax = "";
			String patamarQuedaDadosMin = "";
			String patamarQuedaDadosMax = "";

			ConfiguracaoFacade configuracaoFacade = (ConfiguracaoFacade)Factory.getInstance().get("configuracaoFacade");
			Configuracao configuracao = null;

			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_ACESSO_VOZ_MIN");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarAcessoVozMin = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar minimo de acesso voz no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_ACESSO_VOZ_MAX");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarAcessoVozMax = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar maximo de acesso voz no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_QUEDA_VOZ_MIN");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarQuedaVozMin = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar minimo de queda voz no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_QUEDA_VOZ_MAX");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarQuedaVozMax = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar maximo de queda voz no serviço Mobile Consulta Patamar.");
				}
			}

			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_ACESSO_DADOS_MIN");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarAcessoDadosMin = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar minimo de acesso dados no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_ACESSO_DADOS_MAX");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarAcessoDadosMax = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar maximo de acesso dados no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_QUEDA_DADOS_MIN");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarQuedaDadosMin = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar minimo de queda dados no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_QUEDA_DADOS_MAX");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarQuedaDadosMax = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar maximo de queda dados no serviço Mobile Consulta Patamar.");
				}
			}

			response.setPatamarMinAcessoVoz(patamarAcessoVozMin);
			response.setPatamarMaxAcessoVoz(patamarAcessoVozMax);
			response.setPatamarMinQuedaVoz(patamarQuedaVozMin);
			response.setPatamarMaxQuedaVoz(patamarQuedaVozMax);
			response.setPatamarMinAcessoDados(patamarAcessoDadosMin);
			response.setPatamarMaxAcessoDados(patamarAcessoDadosMax);
			response.setPatamarMinQuedaDados(patamarQuedaDadosMin);
			response.setPatamarMaxQuedaDados(patamarQuedaDadosMax);

			response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.consultaPatamar.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Consulta Patamar.", ex);
			return response;
		}
		return response;
	}

	/**
	 * Retorna todas as tabelas de dominios como um response 
	 * @param request
	 * @return
	 */
	@POST
	@Path("/consultaDominios")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ConsultaDominiosResponse consultaDominios(ConsultaDominiosRequest request) {
		
		ConsultaDominiosResponse response = new ConsultaDominiosResponse();
		
		try {
		
			if (!request.validar()) {
				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Consulta Dominios.");
				return response;
			}

			String token = request.getToken();
			if (!validarToken(token)) {
				logger.error("Sessão inválida no serviço Consulta Dominios.");
				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.tokenInvalido"));
				return response;
			}

			OperadoraFacade operadoraFacade = (OperadoraFacade)Factory.getInstance().get("operadoraFacade");
			List<co.salutary.mobisaude.services.model.operadora.Operadora> lstOperadora = operadoraFacade.list();
			if (lstOperadora != null) {
				List<Operadora> lstRetorno = new ArrayList<Operadora>();
				for (co.salutary.mobisaude.services.model.operadora.Operadora operadora:lstOperadora) {
					Operadora op = new Operadora();
					op.setId(Integer.toString(operadora.getIdOperadora()));
					op.setCodigo(operadora.getCodigo());
					op.setNome(operadora.getNome());

					lstRetorno.add(op);
				}
				response.setOperadoras(lstRetorno.toArray(new Operadora[0]));				
			} else {
				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.erroBuscandoDominioOperadora"));
				logger.warn("Erro listando dominio operadora no serviço Mobile Consulta Dominios.");
				return response;
			}

			TipoSistemaOperacionalFacade tipoSistemaOperacionalFacade = (TipoSistemaOperacionalFacade)Factory.getInstance().get("tipoSistemaOperacionalFacade");
			List<co.salutary.mobisaude.services.model.tiposistemaoperacional.TipoSistemaOperacional> lstTipoSistemaOperacional = tipoSistemaOperacionalFacade.list();
			if (lstTipoSistemaOperacional != null) {
				List<TipoSistemaOperacional> lstRetorno = new ArrayList<TipoSistemaOperacional>();
				for (co.salutary.mobisaude.services.model.tiposistemaoperacional.TipoSistemaOperacional tipoSistemaOperacional:lstTipoSistemaOperacional) {
					TipoSistemaOperacional tso = new TipoSistemaOperacional();
					tso.setId(Integer.toString(tipoSistemaOperacional.getIdTipoSistemaOperacional()));
					tso.setDescricao(tipoSistemaOperacional.getDescricao());

					lstRetorno.add(tso);
				}
				response.setTiposSistemaOperacional((lstRetorno.toArray(new TipoSistemaOperacional[0])));

				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.sucesso"));
			} else {
				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.erroBuscandoDominioTipoSistemaOperacional"));
				logger.warn("Erro listando dominio tipo sistema operacional no serviço Mobile Consulta Dominios.");
				return response;
			}			

			// TipoEstabelecimentoSaudeMsg
			TipoEstabelecimentoSaudeFacade tipoEstabelecimentoSaudeFacade = (TipoEstabelecimentoSaudeFacade)Factory.getInstance().get("tipoEstabelecimentoSaudeFacade");
			List<co.salutary.mobisaude.services.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude> lstTipoEstabelecimentoSaude = tipoEstabelecimentoSaudeFacade.list();
			if (lstTipoEstabelecimentoSaude != null) {
				List<TipoEstabelecimentoSaudeMsg> lstRetorno = new ArrayList<TipoEstabelecimentoSaudeMsg>();
				for (co.salutary.mobisaude.services.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude tipoEstabelecimentoSaude:lstTipoEstabelecimentoSaude) {
					TipoEstabelecimentoSaudeMsg tes = new TipoEstabelecimentoSaudeMsg();
					tes.setId(Integer.toString(tipoEstabelecimentoSaude.getIdTipoEstabelecimentoSaude()));
					tes.setNome(tipoEstabelecimentoSaude.getNome());

					lstRetorno.add(tes);
				}
				response.setTiposEstabelecimentoSaude((lstRetorno.toArray(new TipoEstabelecimentoSaudeMsg[0])));

				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.sucesso"));
			} else {
				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.erroBuscandoDominioTipoEstabelecimentoSaude"));
				logger.warn("Erro listando dominio tipo serviço no serviço Mobile Consulta Dominios.");
				return response;
			}
			
			// TipoGestaoMsg
			TipoGestaoFacade tipoGestaoFacade = (TipoGestaoFacade)Factory.getInstance().get("tipoGestaoFacade");
			List<co.salutary.mobisaude.services.model.tipogestao.TipoGestao> lstTipoGestao = tipoGestaoFacade.list();
			if (lstTipoGestao != null) {
				List<TipoGestaoMsg> lstRetorno = new ArrayList<TipoGestaoMsg>();
				for (co.salutary.mobisaude.services.model.tipogestao.TipoGestao tipoGestao:lstTipoGestao) {
					TipoGestaoMsg tg = new TipoGestaoMsg();
					tg.setId(Integer.toString(tipoGestao.getIdTipoGestao()));
					tg.setNome(tipoGestao.getNome());

					lstRetorno.add(tg);
				}
				response.setTipoGestao((lstRetorno.toArray(new TipoGestaoMsg[0])));

				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.sucesso"));
			} else {
				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.erroBuscandoDominioTipoGestao"));
				logger.warn("Erro listando dominio tipo serviço no serviço Mobile Consulta Dominios.");
				return response;
			}
			
			// RegiaoMsg
			RegiaoFacade regiaoFacade = (RegiaoFacade)Factory.getInstance().get("regiaoFacade");
			List<Regiao> lstRegiao = regiaoFacade.list();
			if (lstRegiao != null) {
				List<RegiaoMsg> lstRetorno = new ArrayList<RegiaoMsg>();
				for (Regiao regiao:lstRegiao) {
					RegiaoMsg r = new RegiaoMsg();
					r.setId(Integer.toString(regiao.getIdRegiao()));
					r.setNome(regiao.getNome());

					lstRetorno.add(r);
				}
				response.setRegiao((lstRetorno.toArray(new RegiaoMsg[0])));

				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.sucesso"));
			} else {
				response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.erroBuscandoDominioRegiao"));
				logger.warn("Erro listando dominio regiao Consulta Dominios.");
				return response;
			}

			response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.consultadominios.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Consulta Dominios.", ex);
			return response;
		}
		return response;
	}
	
	/**
	 * Metodo que trata o request de consulta telas 
	 * @param request
	 * @return
	 */
	@POST
	@Path("/consultaTelas")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ConsultaTelasResponse consultaTelas(ConsultaTelasRequest request) {
		ConsultaTelasResponse response = new ConsultaTelasResponse();
		try {
			if (!request.validar()) {
				response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Consulta Telas.");
				return response;
			}

			String token = request.getToken();

			if (!validarToken(token)) {
				logger.error("Sessão inválida no serviço Consulta Telas.");
				response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.tokenInvalido"));
				return response;
			}


			ConfiguracaoFacade configuracaoFacade = (ConfiguracaoFacade)Factory.getInstance().get("configuracaoFacade");
			Configuracao configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DISPONIBILIDADE);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDisponibilidade(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.erroRankingDisponibilidade"));
					logger.error("Erro obtendo configuracao do ranking de disponibilidade no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_VOZ);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setVoz(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.erroRankingVoz"));
					logger.error("Erro obtendo configuracao do ranking de voz no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDados(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.erroRankingDados"));
					logger.error("Erro obtendo configuracao do ranking de dados no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_2G);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDados2g(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.erroRankingDados2g"));
					logger.error("Erro obtendo configuracao do ranking de dados 2G no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_3G);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDados3g(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.erroRankingDados3g"));
					logger.error("Erro obtendo configuracao do ranking de dados 3G no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_4G);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDados4g(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.erroRankingDados4g"));
					logger.error("Erro obtendo configuracao do ranking de dados 4G no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_GLOBAL);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDadosGlobal(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.erroRankingDadosGlobal"));
					logger.error("Erro obtendo configuracao do ranking de dados global no serviço Mobile Consulta Telas.");
				}
			}

			response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("gateway.mobile.consultatelas.msg.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Consulta Telas.", ex);
			return response;
		}
		return response;
	}	

}