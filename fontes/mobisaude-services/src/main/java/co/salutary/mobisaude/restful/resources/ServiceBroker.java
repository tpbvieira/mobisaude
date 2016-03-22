package co.salutary.mobisaude.restful.resources;

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

import co.salutary.mobisaude.model.Factory;
import co.salutary.mobisaude.model.configuracao.Configuracao;
import co.salutary.mobisaude.model.configuracao.facade.ConfiguracaoFacade;
import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;
import co.salutary.mobisaude.model.estabelecimentosaude.facade.EstabelecimentoSaudeFacade;
import co.salutary.mobisaude.model.municipio.facade.MunicipiosIbgeFacade;
import co.salutary.mobisaude.model.operadora.facade.OperadoraFacade;
import co.salutary.mobisaude.model.regiao.Regiao;
import co.salutary.mobisaude.model.regiao.facade.RegiaoFacade;
import co.salutary.mobisaude.model.tipoestabelecimentosaude.facade.TipoEstabelecimentoSaudeFacade;
import co.salutary.mobisaude.model.tipogestao.facade.TipoGestaoFacade;
import co.salutary.mobisaude.model.tiposistemaoperacional.facade.TipoSistemaOperacionalFacade;
import co.salutary.mobisaude.restful.message.mobile.ConsultaDominiosRequest;
import co.salutary.mobisaude.restful.message.mobile.ConsultaDominiosResponse;
import co.salutary.mobisaude.restful.message.mobile.ConsultaPatamarRequest;
import co.salutary.mobisaude.restful.message.mobile.ConsultaPatamarResponse;
import co.salutary.mobisaude.restful.message.mobile.ConsultaTelasRequest;
import co.salutary.mobisaude.restful.message.mobile.ConsultaTelasResponse;
import co.salutary.mobisaude.restful.message.mobile.ESMsg;
import co.salutary.mobisaude.restful.message.mobile.GeocodeRequest;
import co.salutary.mobisaude.restful.message.mobile.GeocodeResponse;
import co.salutary.mobisaude.restful.message.mobile.GerarChaveRequest;
import co.salutary.mobisaude.restful.message.mobile.GerarChaveResponse;
import co.salutary.mobisaude.restful.message.mobile.GerarTokenRequest;
import co.salutary.mobisaude.restful.message.mobile.GerarTokenResponse;
import co.salutary.mobisaude.restful.message.mobile.GetESRequest;
import co.salutary.mobisaude.restful.message.mobile.GetESResponse;
import co.salutary.mobisaude.restful.message.mobile.Operadora;
import co.salutary.mobisaude.restful.message.mobile.RegiaoMsg;
import co.salutary.mobisaude.restful.message.mobile.TextoAjudaRequest;
import co.salutary.mobisaude.restful.message.mobile.TextoAjudaResponse;
import co.salutary.mobisaude.restful.message.mobile.TextoAjudaServicoMovelRequest;
import co.salutary.mobisaude.restful.message.mobile.TextoAjudaServicoMovelResponse;
import co.salutary.mobisaude.restful.message.mobile.TextoAvisoRelatarProblemaRequest;
import co.salutary.mobisaude.restful.message.mobile.TextoAvisoRelatarProblemaResponse;
import co.salutary.mobisaude.restful.message.mobile.TipoEstabelecimentoSaudeMsg;
import co.salutary.mobisaude.restful.message.mobile.TipoGestaoMsg;
import co.salutary.mobisaude.restful.message.mobile.TipoSistemaOperacional;

/**
 *	Resource para os servicos 
 *
 */
@Path("/mobile")
@Controller
public class ServiceBroker extends AbstractServiceBroker {
	/**
	 * Logger
	 */
	private static final Log logger = LogFactory.getLog(ServiceBroker.class);

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
	public ServiceBroker() {

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
				logger.error(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				return response;
			}

			String token = request.getToken();

			if (!validarToken(token)) {
				logger.error(properties.getProperty("co.mobisaude.strings.geocode.tokenInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.geocode.tokenInvalido"));
				return response;
			}

			String sLatitude = request.getLatitude();
			String sLongitude = request.getLongitude();
			Double latitude = null;
			Double longitude = null;
			try {
				latitude = Double.parseDouble(sLatitude);
			} catch (Exception ex) {
				logger.error(properties.getProperty("co.mobisaude.strings.geocode.latitudeInvalida"));
				response.setErro(properties.getProperty("co.mobisaude.strings.geocode.latitudeInvalida"));				
				return response;
			}
			try {
				longitude = Double.parseDouble(sLongitude);
			} catch (Exception ex) {
				logger.error(properties.getProperty("co.mobisaude.strings.geocode.longitudeInvalida"));
				response.setErro(properties.getProperty("co.mobisaude.strings.geocode.longitudeInvalida"));				
				return response;
			}

			MunicipiosIbgeFacade municipiosIbgeFacade = (MunicipiosIbgeFacade)Factory.getInstance().get("municipiosIbgeFacade");
			String[] dadosMunicipio = municipiosIbgeFacade.getCodMunicipioByCoord(latitude, longitude);
			if (dadosMunicipio != null) {
				response.setCodMunicipioIbge(dadosMunicipio[0]);
				response.setMunicipio(dadosMunicipio[1]);
				response.setUf(dadosMunicipio[2]);
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.geocode.localidadeNaoEncontrada"));
				response.setErro(properties.getProperty("co.mobisaude.strings.geocode.localidadeNaoEncontrada"));				
			}

		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.strings.geocode.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.strings.geocode.erroProcessandoServico"));			
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
				logger.error(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));				
				return response;
			}

			String chave = request.getChave();
			String token = gerarToken(chave);

			if (token != null) {
				response.setToken(token);
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				response.setErro(properties.getProperty("co.mobisaude.gerartoken.msg.erroGerandoToken"));
				return response;
			}
		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.strings.gerartoken.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.strings.gerartoken.erroProcessandoServico"));			
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
				logger.error(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));				
				return response;
			}


			String chave = gerarChave();

			if (chave != null) {
				response.setChave(chave);
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				response.setErro(properties.getProperty("co.mobisaude.gerarchave.msg.erroGerandoChave"));
				return response;
			}
		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.gerartoken.msg.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.gerartoken.msg.erroProcessandoServico"));			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/listES")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GetESResponse listES(GetESRequest request) {
		GetESResponse response = new GetESResponse();
		
		try {
			if (!request.validar()) {
				logger.error(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));				
				return response;
			}

			String token = request.getToken();

			if (!validarToken(token)) {
				logger.error(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				return response;
			}

			EstabelecimentoSaudeFacade esFacade = (EstabelecimentoSaudeFacade)Factory.getInstance().get("estabelecimentoSaudeFacade");
			List<EstabelecimentoSaude> esList = esFacade.list();
			
			if (esList != null) {
				List<ESMsg> esMsgList = new ArrayList<ESMsg>();
				for (EstabelecimentoSaude es:esList) {
					ESMsg esMsg = new ESMsg();
					esMsg.setLatitude(String.valueOf(es.getLatitude()));
					esMsg.setLongitude(String.valueOf(es.getLongitude()));
					esMsg.setNomeFantasia(es.getNomeFantasia()); 
					esMsg.setTipoEstabelecimentoSaude(String.valueOf(es.getIdTipoEstabelecimentoSaude()));

					esMsgList.add(esMsg);
				}
				response.setEstabelecimentoSaude(esMsgList.toArray(new ESMsg[0]));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.getesbymunicipio.erroProcessandoServico"));
			}
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.strings.getesbymunicipio.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.strings.getesbymunicipio.erroProcessandoServico"));			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/getESByMunicipio")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GetESResponse getESByMunicipio(GetESRequest request) {
		GetESResponse response = new GetESResponse();
		
		try {
			if (!request.validar()) {
				logger.error(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));				
				return response;
			}

			String token = request.getToken();
			String municipio = request.getMunicipio();

			if (!validarToken(token)) {
				logger.error(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				return response;
			}
			if (municipio == null || municipio.trim().equals("")) {
				logger.error(properties.getProperty("co.mobisaude.strings.municipioInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.municipioInvalido"));				
				return response;
			}

			EstabelecimentoSaudeFacade esFacade = (EstabelecimentoSaudeFacade)Factory.getInstance().get("estabelecimentoSaudeFacade");
			List<EstabelecimentoSaude> esList = esFacade.listByMunicipio(municipio);
			
			if (esList != null) {
				List<ESMsg> esMsgList = new ArrayList<ESMsg>();
				for (EstabelecimentoSaude es:esList) {
					ESMsg esMsg = new ESMsg();
					esMsg.setLatitude(String.valueOf(es.getLatitude()));
					esMsg.setLongitude(String.valueOf(es.getLongitude()));
					esMsg.setNomeFantasia(es.getNomeFantasia()); 
					esMsg.setTipoEstabelecimentoSaude(String.valueOf(es.getIdTipoEstabelecimentoSaude()));

					esMsgList.add(esMsg);
				}
				response.setEstabelecimentoSaude(esMsgList.toArray(new ESMsg[0]));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.getesbymunicipio.erroProcessandoServico"));
			}
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.strings.getesbymunicipio.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.strings.getesbymunicipio.erroProcessandoServico"));			
			return response;
		}
		return response;
	}
	
	
	@POST
	@Path("/getESByMunicipioTipoEstabelecimento")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GetESResponse getESByMunicipioTipoEstabelecimento(GetESRequest request) {
		GetESResponse response = new GetESResponse();
		
		try {
			if (!request.validar()) {
				logger.error(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));				
				return response;
			}

			String token = request.getToken();
			String municipio = request.getMunicipio();
			String tipoES = request.getTipoEstabelecimentoSaude();
			String[] tiposES = request.getTiposEstabelecimentoSaude();

			if (!validarToken(token)) {
				logger.error(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				return response;
			}
			if (municipio == null || municipio.trim().equals("")) {
				logger.error(properties.getProperty("co.mobisaude.strings.municipioInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.municipioInvalido"));				
				return response;
			}

			EstabelecimentoSaudeFacade esFacade = (EstabelecimentoSaudeFacade)Factory.getInstance().get("estabelecimentoSaudeFacade");
			List<EstabelecimentoSaude> esList = null;
			
			if (tiposES != null) {
				if (tiposES.length > 0) {
					esList = esFacade.listByMunicipioTiposEstabelecimento(municipio, tiposES);
				} else {
					esList = new ArrayList<EstabelecimentoSaude>();
				}
			} else {
				if (tipoES != null) {
					esList = esFacade.listByMunicipioTipoEstabelecimento(municipio, tipoES);
				} else {
					esList = new ArrayList<EstabelecimentoSaude>();
				}
			}
			
			if (esList != null) {
				List<ESMsg> esMsgList = new ArrayList<ESMsg>();
				for (EstabelecimentoSaude es:esList) {
					ESMsg esMsg = new ESMsg();
					esMsg.setLatitude(String.valueOf(es.getLatitude()));
					esMsg.setLongitude(String.valueOf(es.getLongitude()));
					esMsg.setNomeFantasia(es.getNomeFantasia()); 
					esMsg.setTipoEstabelecimentoSaude(String.valueOf(es.getIdTipoEstabelecimentoSaude()));

					esMsgList.add(esMsg);
				}
				response.setEstabelecimentoSaude(esMsgList.toArray(new ESMsg[0]));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.getesbymunicipio.erroProcessandoServico"));
			}
			
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			
		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.strings.getesbymunicipio.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.strings.getesbymunicipio.erroProcessandoServico"));			
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
					response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
				} else {
					logger.error(properties.getProperty("co.mobisaude.textoAjuda.msg.erroTextoAjuda"));
					response.setErro(properties.getProperty("co.mobisaude.textoAjuda.msg.erroTextoAjuda"));					
				}
			}
		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.textoAjuda.msg.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.textoAjuda.msg.erroProcessandoServico"));			
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
					response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
				} else {
					logger.error(properties.getProperty("co.mobisaude.textoAjudaServicoMovel.msg.erroTextoAjuda"));
					response.setErro(properties.getProperty("co.mobisaude.textoAjudaServicoMovel.msg.erroTextoAjuda"));					
				}
			}
		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.textoAjudaServicoMovel.msg.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.textoAjudaServicoMovel.msg.erroProcessandoServico"));			
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
					response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
				} else {
					logger.error(properties.getProperty("co.mobisaude.textoAvisoRelatarProblema.msg.erroTextoAvisoRelatarProblema"));
					response.setErro(properties.getProperty("co.mobisaude.textoAvisoRelatarProblema.msg.erroTextoAvisoRelatarProblema"));					
				}
			}
		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.textoAvisoRelatarProblema.msg.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.textoAvisoRelatarProblema.msg.erroProcessandoServico"));			
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
					response.setErro(properties.getProperty("co.mobisaude.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar minimo de acesso voz no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_ACESSO_VOZ_MAX");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarAcessoVozMax = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("co.mobisaude.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar maximo de acesso voz no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_QUEDA_VOZ_MIN");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarQuedaVozMin = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("co.mobisaude.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar minimo de queda voz no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_QUEDA_VOZ_MAX");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarQuedaVozMax = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("co.mobisaude.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar maximo de queda voz no serviço Mobile Consulta Patamar.");
				}
			}

			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_ACESSO_DADOS_MIN");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarAcessoDadosMin = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("co.mobisaude.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar minimo de acesso dados no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_ACESSO_DADOS_MAX");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarAcessoDadosMax = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("co.mobisaude.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar maximo de acesso dados no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_QUEDA_DADOS_MIN");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarQuedaDadosMin = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("co.mobisaude.consultaPatamar.msg.erroObtendoConfiguracao"));
					logger.error("Erro obtendo patamar minimo de queda dados no serviço Mobile Consulta Patamar.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao("PATAMAR_QUEDA_DADOS_MAX");
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					patamarQuedaDadosMax = configuracao.getValor(); 
				} else {
					response.setErro(properties.getProperty("co.mobisaude.consultaPatamar.msg.erroObtendoConfiguracao"));
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

			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("co.mobisaude.consultaPatamar.msg.erroProcessandoServico"));
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
				logger.error(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));				
				return response;
			}

			String token = request.getToken();
			if (!validarToken(token)) {
				logger.error(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				return response;
			}

			OperadoraFacade operadoraFacade = (OperadoraFacade)Factory.getInstance().get("operadoraFacade");
			List<co.salutary.mobisaude.model.operadora.Operadora> lstOperadora = operadoraFacade.list();
			if (lstOperadora != null) {
				List<Operadora> lstRetorno = new ArrayList<Operadora>();
				for (co.salutary.mobisaude.model.operadora.Operadora operadora:lstOperadora) {
					Operadora op = new Operadora();
					op.setId(Integer.toString(operadora.getIdOperadora()));
					op.setCodigo(operadora.getCodigo());
					op.setNome(operadora.getNome());

					lstRetorno.add(op);
				}
				response.setOperadoras(lstRetorno.toArray(new Operadora[0]));				
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioOperadora"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioOperadora"));				
				return response;
			}

			TipoSistemaOperacionalFacade tipoSistemaOperacionalFacade = (TipoSistemaOperacionalFacade)Factory.getInstance().get("tipoSistemaOperacionalFacade");
			List<co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional> lstTipoSistemaOperacional = tipoSistemaOperacionalFacade.list();
			if (lstTipoSistemaOperacional != null) {
				List<TipoSistemaOperacional> lstRetorno = new ArrayList<TipoSistemaOperacional>();
				for (co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional tipoSistemaOperacional:lstTipoSistemaOperacional) {
					TipoSistemaOperacional tso = new TipoSistemaOperacional();
					tso.setId(Integer.toString(tipoSistemaOperacional.getIdTipoSistemaOperacional()));
					tso.setDescricao(tipoSistemaOperacional.getDescricao());

					lstRetorno.add(tso);
				}
				response.setTiposSistemaOperacional((lstRetorno.toArray(new TipoSistemaOperacional[0])));

				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoSistemaOperacional"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoSistemaOperacional"));				
				return response;
			}			

			// TipoEstabelecimentoSaudeMsg
			TipoEstabelecimentoSaudeFacade tipoEstabelecimentoSaudeFacade = (TipoEstabelecimentoSaudeFacade)Factory.getInstance().get("tipoEstabelecimentoSaudeFacade");
			List<co.salutary.mobisaude.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude> lstTipoEstabelecimentoSaude = tipoEstabelecimentoSaudeFacade.list();
			if (lstTipoEstabelecimentoSaude != null) {
				List<TipoEstabelecimentoSaudeMsg> lstRetorno = new ArrayList<TipoEstabelecimentoSaudeMsg>();
				for (co.salutary.mobisaude.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude tipoEstabelecimentoSaude:lstTipoEstabelecimentoSaude) {
					TipoEstabelecimentoSaudeMsg tes = new TipoEstabelecimentoSaudeMsg();
					tes.setId(Integer.toString(tipoEstabelecimentoSaude.getIdTipoEstabelecimentoSaude()));
					tes.setNome(tipoEstabelecimentoSaude.getNome());

					lstRetorno.add(tes);
				}
				response.setTiposEstabelecimentoSaude((lstRetorno.toArray(new TipoEstabelecimentoSaudeMsg[0])));
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoEstabelecimentoSaude"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoEstabelecimentoSaude"));				
				return response;
			}
			
			// TipoGestaoMsg
			TipoGestaoFacade tipoGestaoFacade = (TipoGestaoFacade)Factory.getInstance().get("tipoGestaoFacade");
			List<co.salutary.mobisaude.model.tipogestao.TipoGestao> lstTipoGestao = tipoGestaoFacade.list();
			if (lstTipoGestao != null) {
				List<TipoGestaoMsg> lstRetorno = new ArrayList<TipoGestaoMsg>();
				for (co.salutary.mobisaude.model.tipogestao.TipoGestao tipoGestao:lstTipoGestao) {
					TipoGestaoMsg tg = new TipoGestaoMsg();
					tg.setId(Integer.toString(tipoGestao.getIdTipoGestao()));
					tg.setNome(tipoGestao.getNome());

					lstRetorno.add(tg);
				}
				response.setTipoGestao((lstRetorno.toArray(new TipoGestaoMsg[0])));
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoGestao"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoGestao"));				
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
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioRegiao"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioRegiao"));				
				return response;
			}

			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.strings.erroProcessandoServico"), ex);
			response.setErro(properties.getProperty("co.mobisaude.strings.erroProcessandoServico"));			
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
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				logger.error("Request inválido no serviço Mobile Consulta Telas.");
				return response;
			}

			String token = request.getToken();

			if (!validarToken(token)) {
				logger.error("Sessão inválida no serviço Consulta Telas.");
				response.setErro(properties.getProperty("co.mobisaude.strings.consultatelas.tokenInvalido"));
				return response;
			}


			ConfiguracaoFacade configuracaoFacade = (ConfiguracaoFacade)Factory.getInstance().get("configuracaoFacade");
			Configuracao configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DISPONIBILIDADE);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDisponibilidade(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("co.mobisaude.strings.consultatelas.erroRankingDisponibilidade"));
					logger.error("Erro obtendo configuracao do ranking de disponibilidade no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_VOZ);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setVoz(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("co.mobisaude.strings.consultatelas.erroRankingVoz"));
					logger.error("Erro obtendo configuracao do ranking de voz no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDados(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("co.mobisaude.strings.consultatelas.erroRankingDados"));
					logger.error("Erro obtendo configuracao do ranking de dados no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_2G);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDados2g(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("co.mobisaude.strings.consultatelas.erroRankingDados2g"));
					logger.error("Erro obtendo configuracao do ranking de dados 2G no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_3G);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDados3g(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("co.mobisaude.strings.consultatelas.erroRankingDados3g"));
					logger.error("Erro obtendo configuracao do ranking de dados 3G no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_4G);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDados4g(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("co.mobisaude.strings.consultatelas.erroRankingDados4g"));
					logger.error("Erro obtendo configuracao do ranking de dados 4G no serviço Mobile Consulta Telas.");
				}
			}
			configuracao = configuracaoFacade.getConfiguracao(EXIBIR_RANKING_DADOS_GLOBAL);
			if (configuracao != null) {
				if (configuracao.getValor() != null) {
					response.setDadosGlobal(configuracao.getValor().equals("S") ? "true" : "false");
				} else {
					response.setErro(properties.getProperty("co.mobisaude.strings.consultatelas.erroRankingDadosGlobal"));
					logger.error("Erro obtendo configuracao do ranking de dados global no serviço Mobile Consulta Telas.");
				}
			}

			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
		} catch (Exception ex) {
			response.setErro(properties.getProperty("co.mobisaude.strings.consultatelas.erroProcessandoServico"));
			logger.error("Erro no serviço Mobile Consulta Telas.", ex);
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
	
}