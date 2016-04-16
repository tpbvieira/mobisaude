package co.salutary.mobisaude.restful.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import co.salutary.mobisaude.exception.MobisaudeServicesException;
import co.salutary.mobisaude.model.Factory;
import co.salutary.mobisaude.model.avaliacao.Avaliacao;
import co.salutary.mobisaude.model.avaliacao.facade.AvaliacaoFacade;
import co.salutary.mobisaude.model.avaliacaomediames.AvaliacaoMediaMes;
import co.salutary.mobisaude.model.avaliacaomediames.facade.AvaliacaoMediaMesFacade;
import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;
import co.salutary.mobisaude.model.estabelecimentosaude.facade.EstabelecimentoSaudeFacade;
import co.salutary.mobisaude.model.municipio.facade.MunicipiosIbgeFacade;
import co.salutary.mobisaude.model.regiao.Regiao;
import co.salutary.mobisaude.model.regiao.facade.RegiaoFacade;
import co.salutary.mobisaude.model.sugestao.Sugestao;
import co.salutary.mobisaude.model.sugestao.facade.SugestaoFacade;
import co.salutary.mobisaude.model.tipoestabelecimentosaude.facade.TipoEstabelecimentoSaudeFacade;
import co.salutary.mobisaude.model.tipogestao.facade.TipoGestaoFacade;
import co.salutary.mobisaude.model.tiposistemaoperacional.facade.TipoSistemaOperacionalFacade;
import co.salutary.mobisaude.model.user.User;
import co.salutary.mobisaude.model.user.facade.UserFacade;
import co.salutary.mobisaude.restful.message.mobile.AvaliacaoDTO;
import co.salutary.mobisaude.restful.message.mobile.AvaliacaoMediaMesDTO;
import co.salutary.mobisaude.restful.message.mobile.EsDTO;
import co.salutary.mobisaude.restful.message.mobile.RegiaoDTO;
import co.salutary.mobisaude.restful.message.mobile.TipoEstabelecimentoSaudeDTO;
import co.salutary.mobisaude.restful.message.mobile.TipoGestaoDTO;
import co.salutary.mobisaude.restful.message.mobile.TipoSistemaOperacionalDTO;
import co.salutary.mobisaude.restful.message.request.AvaliacaoMediaMesRequest;
import co.salutary.mobisaude.restful.message.request.AvaliacaoRequest;
import co.salutary.mobisaude.restful.message.request.ConsultaDominiosRequest;
import co.salutary.mobisaude.restful.message.request.ESRequest;
import co.salutary.mobisaude.restful.message.request.GeocodeRequest;
import co.salutary.mobisaude.restful.message.request.GerarChaveRequest;
import co.salutary.mobisaude.restful.message.request.GerarTokenRequest;
import co.salutary.mobisaude.restful.message.request.SugestaoRequest;
import co.salutary.mobisaude.restful.message.request.UserRequest;
import co.salutary.mobisaude.restful.message.response.AvaliacaoMediaMesResponse;
import co.salutary.mobisaude.restful.message.response.AvaliacaoResponse;
import co.salutary.mobisaude.restful.message.response.ConsultaDominiosResponse;
import co.salutary.mobisaude.restful.message.response.ESResponse;
import co.salutary.mobisaude.restful.message.response.GeocodeResponse;
import co.salutary.mobisaude.restful.message.response.GerarChaveResponse;
import co.salutary.mobisaude.restful.message.response.GerarTokenResponse;
import co.salutary.mobisaude.restful.message.response.SugestaoResponse;
import co.salutary.mobisaude.restful.message.response.UserResponse;
import co.salutary.mobisaude.util.Utils;

@Path("/mobile")
@Controller
public class ServiceBroker extends AbstractServiceBroker {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static final Log logger = LogFactory.getLog(ServiceBroker.class);

	public ServiceBroker() {

	}

	@POST
	@Path("/gerarToken")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GerarTokenResponse gerarToken(GerarTokenRequest request) {logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		GerarTokenResponse response = new GerarTokenResponse();
		try {
			if (!request.validate()) {
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

	@POST
	@Path("/gerarChave")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GerarChaveResponse gerarChave(GerarChaveRequest request) {logger.info(new Object() {}.getClass().getEnclosingMethod().getName());
		GerarChaveResponse response = new GerarChaveResponse();
		try {
			
			if (!request.validate()) {
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
	@Path("/geocode")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GeocodeResponse geocode(GeocodeRequest request) {logger.info(new Object() {}.getClass().getEnclosingMethod().getName());
		GeocodeResponse response = new GeocodeResponse();
		try {
			if (!request.validate()) {
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

	@POST
	@Path("/consultaDominios")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ConsultaDominiosResponse consultaDominios(ConsultaDominiosRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());
		
		ConsultaDominiosResponse response = new ConsultaDominiosResponse();
		
		try {		
			if (!request.validate()) {
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

			TipoSistemaOperacionalFacade tipoSistemaOperacionalFacade = (TipoSistemaOperacionalFacade)Factory.getInstance().get("tipoSistemaOperacionalFacade");
			List<co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional> lstTipoSistemaOperacional = tipoSistemaOperacionalFacade.list();
			if (lstTipoSistemaOperacional != null) {
				List<TipoSistemaOperacionalDTO> lstRetorno = new ArrayList<TipoSistemaOperacionalDTO>();
				for (co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional tipoSistemaOperacional:lstTipoSistemaOperacional) {
					TipoSistemaOperacionalDTO tso = new TipoSistemaOperacionalDTO();
					tso.setId(Integer.toString(tipoSistemaOperacional.getIdTipoSistemaOperacional()));
					tso.setDescricao(tipoSistemaOperacional.getDescricao());

					lstRetorno.add(tso);
				}
				response.setTiposSistemaOperacional((lstRetorno.toArray(new TipoSistemaOperacionalDTO[0])));
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoSistemaOperacional"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoSistemaOperacional"));				
				return response;
			}			

			TipoEstabelecimentoSaudeFacade tipoEstabelecimentoSaudeFacade = (TipoEstabelecimentoSaudeFacade)Factory.getInstance().get("tipoEstabelecimentoSaudeFacade");
			List<co.salutary.mobisaude.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude> lstTipoEstabelecimentoSaude = tipoEstabelecimentoSaudeFacade.list();
			if (lstTipoEstabelecimentoSaude != null) {
				List<TipoEstabelecimentoSaudeDTO> lstRetorno = new ArrayList<TipoEstabelecimentoSaudeDTO>();
				for (co.salutary.mobisaude.model.tipoestabelecimentosaude.TipoEstabelecimentoSaude tipoEstabelecimentoSaude:lstTipoEstabelecimentoSaude) {
					TipoEstabelecimentoSaudeDTO tes = new TipoEstabelecimentoSaudeDTO();
					tes.setId(Integer.toString(tipoEstabelecimentoSaude.getIdTipoEstabelecimentoSaude()));
					tes.setNome(tipoEstabelecimentoSaude.getNome());

					lstRetorno.add(tes);
				}
				response.setTiposEstabelecimentoSaude((lstRetorno.toArray(new TipoEstabelecimentoSaudeDTO[0])));
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoEstabelecimentoSaude"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoEstabelecimentoSaude"));				
				return response;
			}
			
			RegiaoFacade regiaoFacade = (RegiaoFacade)Factory.getInstance().get("regiaoFacade");
			List<Regiao> lstRegiao = regiaoFacade.list();
			if (lstRegiao != null) {
				List<RegiaoDTO> lstRetorno = new ArrayList<RegiaoDTO>();
				for (Regiao regiao:lstRegiao) {
					RegiaoDTO r = new RegiaoDTO();
					r.setId(Integer.toString(regiao.getIdRegiao()));
					r.setNome(regiao.getNome());					
					lstRetorno.add(r);
				}
				response.setRegiao((lstRetorno.toArray(new RegiaoDTO[0])));
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioRegiao"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioRegiao"));				
				return response;
			}
			
			TipoGestaoFacade tipoGestaoFacade = (TipoGestaoFacade)Factory.getInstance().get("tipoGestaoFacade");
			List<co.salutary.mobisaude.model.tipogestao.TipoGestao> lstTipoGestao = tipoGestaoFacade.list();
			if (lstTipoGestao != null) {
				List<TipoGestaoDTO> lstRetorno = new ArrayList<TipoGestaoDTO>();
				for (co.salutary.mobisaude.model.tipogestao.TipoGestao tipoGestao:lstTipoGestao) {
					TipoGestaoDTO tg = new TipoGestaoDTO();
					tg.setId(Integer.toString(tipoGestao.getIdTipoGestao()));
					tg.setNome(tipoGestao.getNome());
					lstRetorno.add(tg);
				}
				response.setTiposGestao((lstRetorno.toArray(new TipoGestaoDTO[0])));
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoGestao"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioTipoGestao"));				
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
	
	@POST
	@Path("/listES")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ESResponse listES(ESRequest request) {
		ESResponse response = new ESResponse();
		
		try {
			if (!request.validate()) {
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
				List<EsDTO> esMsgList = new ArrayList<EsDTO>();
				for (EstabelecimentoSaude es:esList) {
					EsDTO esMsg = new EsDTO();
					esMsg.setIdCnes(String.valueOf(es.getIdCnes()));
					esMsg.setLatitude(String.valueOf(es.getLatitude()));
					esMsg.setLongitude(String.valueOf(es.getLongitude()));
					esMsg.setNomeFantasia(es.getNomeFantasia()); 
					esMsg.setIdTipoEstabelecimentoSaude(String.valueOf(es.getIdTipoEstabelecimentoSaude()));

					esMsgList.add(esMsg);
				}
				response.setEstabelecimentoSaude(esMsgList.toArray(new EsDTO[0]));
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
	@Path("/getESByIdMunicipio")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ESResponse getESByIdMunicipio(ESRequest request) {
		ESResponse response = new ESResponse();
		
		try {
			if (!request.validate()) {
				logger.error(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));				
				return response;
			}

			String token = request.getToken();
			String idMunicipio = request.getIdMunicipio();

			if (!validarToken(token)) {
				logger.error(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				return response;
			}
			if (idMunicipio == null || idMunicipio.trim().equals("")) {
				logger.error(properties.getProperty("co.mobisaude.strings.municipioInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.municipioInvalido"));				
				return response;
			}

			EstabelecimentoSaudeFacade esFacade = (EstabelecimentoSaudeFacade)Factory.getInstance().get("estabelecimentoSaudeFacade");
			List<EstabelecimentoSaude> esList = esFacade.listByIdMunicipio(idMunicipio);
			
			if (esList != null) {
				List<EsDTO> esMsgList = new ArrayList<EsDTO>();
				for (EstabelecimentoSaude es:esList) {
					EsDTO esMsg = new EsDTO();
					esMsg.setIdCnes(String.valueOf(es.getIdCnes()));
					esMsg.setLatitude(String.valueOf(es.getLatitude()));
					esMsg.setLongitude(String.valueOf(es.getLongitude()));
					esMsg.setNomeFantasia(es.getNomeFantasia()); 
					esMsg.setIdTipoEstabelecimentoSaude(String.valueOf(es.getIdTipoEstabelecimentoSaude()));

					esMsgList.add(esMsg);
				}
				response.setEstabelecimentoSaude(esMsgList.toArray(new EsDTO[0]));
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
	@Path("/getESByIdMunicipioIdTipoES")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ESResponse getESByIdMunicipioIdTipoES(ESRequest request) {
		ESResponse response = new ESResponse();
		
		try {
			if (!request.validate()) {
				logger.error(properties.getProperty("co.mobisaude.strings.requestInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.requestInvalido"));				
				return response;
			}

			String token = request.getToken();
			String idMunicipio = request.getIdMunicipio();
			String idTipoES = request.getIdTipoEstabelecimentoSaude();
			String[] idTiposES = request.getIdTiposEstabelecimentoSaude();

			if (!validarToken(token)) {
				logger.error(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.tokenInvalido"));
				return response;
			}
			if (idMunicipio == null || idMunicipio.trim().equals("")) {
				logger.error(properties.getProperty("co.mobisaude.strings.municipioInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.municipioInvalido"));				
				return response;
			}

			EstabelecimentoSaudeFacade esFacade = (EstabelecimentoSaudeFacade)Factory.getInstance().get("estabelecimentoSaudeFacade");
			List<EstabelecimentoSaude> esList = null;
			
			if (idTiposES != null) {
				if (idTiposES.length > 0) {
					esList = esFacade.listByIdMunicipioIdTiposEstabelecimento(idMunicipio, idTiposES);
				} else {
					esList = new ArrayList<EstabelecimentoSaude>();
				}
			} else {
				if (idTipoES != null) {
					esList = esFacade.listByIdMunicipioIdTipoEstabelecimento(idMunicipio, idTipoES);
				} else {
					esList = new ArrayList<EstabelecimentoSaude>();
				}
			}
			
			if (esList != null) {
				List<EsDTO> esMsgList = new ArrayList<EsDTO>();
				for (EstabelecimentoSaude es:esList) {
					EsDTO esMsg = new EsDTO();
					esMsg.setIdCnes(String.valueOf(es.getIdCnes()));
					esMsg.setLatitude(String.valueOf(es.getLatitude()));
					esMsg.setLongitude(String.valueOf(es.getLongitude()));
					esMsg.setNomeFantasia(es.getNomeFantasia()); 
					esMsg.setIdTipoEstabelecimentoSaude(String.valueOf(es.getIdTipoEstabelecimentoSaude()));

					esMsgList.add(esMsg);
				}
				response.setEstabelecimentoSaude(esMsgList.toArray(new EsDTO[0]));
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
	@Path("/signup")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public UserResponse signup(UserRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		UserResponse response = new UserResponse();
		try {
			if (!request.validate()) {
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

			String email = request.getEmail();
			String password = request.getPassword();
			String name = request.getName();
			String phone = request.getPhone();
			boolean isContactable = request.isContactable();
			User newUser = new User(email,password,name,phone, isContactable);

			UserFacade userFacade = (UserFacade)Factory.getInstance().get("userFacade");
			userFacade.save(newUser);
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (DataIntegrityViolationException e) {
			logger.error(properties.getProperty("co.mobisaude.strings.user.notunique"), e);
			response.setErro(e.getMessage());			
			return response;
		} catch (Exception e) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), e);
			response.setErro(e.getMessage());			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/updateUser")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public UserResponse updateUser(UserRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		UserResponse response = new UserResponse();
		try {
			if (!request.validate()) {
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

			String email = request.getEmail();
			String password = request.getPassword();
			String name = request.getName();
			String phone = request.getPhone();
			boolean isContactable = request.isContactable();
			User newUser = new User(email,password,name,phone, isContactable);

			UserFacade userFacade = (UserFacade)Factory.getInstance().get("userFacade");
			newUser = userFacade.update(newUser);
			response.setEmail(newUser.getEmail());
			response.setPassword(newUser.getPassword());
			response.setName(newUser.getName());
			response.setPhone(newUser.getPhone());
			response.setContactable(newUser.isContactable());
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (Exception ex) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), ex);
			response.setErro(ex.getMessage());			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/getUser")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public UserResponse getUser(UserRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		UserResponse response = new UserResponse();
		try {
			if (!request.validate()) {
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

			UserFacade userFacade = (UserFacade)Factory.getInstance().get("userFacade");
			User newUser = userFacade.get(request.getEmail());
			response.setEmail(newUser.getEmail());
			response.setPassword(newUser.getPassword());
			response.setName(newUser.getName());
			response.setPhone(newUser.getPhone());
			response.setContactable(newUser.isContactable());
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (Exception ex) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), ex);
			response.setErro(ex.getMessage());			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/signin")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public UserResponse signin(UserRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		UserResponse response = new UserResponse();
		try {
			if (!request.validate()) {
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

			UserFacade userFacade = (UserFacade)Factory.getInstance().get("userFacade");
			User user = userFacade.get(request.getEmail());
			if(user.getEmail().equals(request.getEmail()) && user.getPassword().equals(request.getPassword())){
				response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			}else{
				throw new MobisaudeServicesException("Login ou Senha inválido");
			}

		} catch (Exception ex) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), ex);
			response.setErro(ex.getMessage());			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/sugerir")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public SugestaoResponse sugerir(SugestaoRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		SugestaoResponse response = new SugestaoResponse();
		try {

			if (!request.validate()) {
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

			String idEstabelecimentoSaude = request.getIdEstabelecimentoSaude();
			String email = request.getEmail();
			String sugestaoStr = request.getSugestao();
			
			Sugestao sugestao = new Sugestao(Integer.valueOf(idEstabelecimentoSaude), email, sugestaoStr, new Date());

			SugestaoFacade sugestaoFacade = (SugestaoFacade)Factory.getInstance().get("sugestaoFacade");
			sugestaoFacade.save(sugestao);
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (DataIntegrityViolationException e) {
			logger.error(properties.getProperty("co.mobisaude.strings.user.notunique"), e);
			response.setErro(e.getMessage());			
			return response;
		} catch (Exception e) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), e);
			response.setErro(e.getMessage());			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/getSugestao")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public SugestaoResponse getSugestao(SugestaoRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		SugestaoResponse response = new SugestaoResponse();
		try {

			if (!request.validate()) {
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

			String idEstabelecimentoSaude = request.getIdEstabelecimentoSaude();
			String email = request.getEmail();			
			
			SugestaoFacade sugestaoFacade = (SugestaoFacade)Factory.getInstance().get("sugestaoFacade");
			Sugestao sugestao = sugestaoFacade.getSugestao(Integer.valueOf(idEstabelecimentoSaude), email);
			response.setIdEstabelecimentoSaude(sugestao.getIdEstabelecimentoSaude().toString());
			response.setEmail(sugestao.getEmail());
			response.setSugestao(sugestao.getSugestao());
			response.setDate(sdf.format(sugestao.getDate()));
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (DataIntegrityViolationException e) {
			logger.error(properties.getProperty("co.mobisaude.strings.user.notunique"), e);
			response.setErro(e.getMessage());			
			return response;
		} catch (Exception e) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), e);
			response.setErro(e.getMessage());			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/avaliar")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public AvaliacaoResponse avaliar(AvaliacaoRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		AvaliacaoResponse response = new AvaliacaoResponse();
		try {

			if (!request.validate()) {
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

			// Save Avaliacao
			String idEstabelecimentoSaude = request.getIdEstabelecimentoSaude();
			String email = request.getEmail();
			String avaliacaoStr = request.getAvaliacao();
			String titulo = request.getTitulo();
			String rating = request.getRating();			
			Avaliacao avaliacao = new Avaliacao(Integer.valueOf(idEstabelecimentoSaude), email, titulo, avaliacaoStr, Float.valueOf(rating));
			AvaliacaoFacade avaliacaoFacade = (AvaliacaoFacade)Factory.getInstance().get("avaliacaoFacade");
			avaliacaoFacade.save(avaliacao);
			
			// Get evaluations for mean calculation 
			List<Avaliacao> avaliacoes = avaliacaoFacade.listByIdEstabelecimentoSaude(Integer.valueOf(idEstabelecimentoSaude));
			float mean = 0;
			for(Avaliacao av: avaliacoes){
				mean = mean + av.getRating();
			}
			mean = mean/avaliacoes.size();
			
			//Update AvaliacaoMediaMes			
			AvaliacaoMediaMesRequest avaliacaoMediaRequest = new AvaliacaoMediaMesRequest();
			avaliacaoMediaRequest.setToken(token);
			avaliacaoMediaRequest.setIdEstabelecimentoSaude(idEstabelecimentoSaude);
			avaliacaoMediaRequest.setRating(Float.toString(mean));
			Date current = new Date();
			avaliacaoMediaRequest.setDate(Utils.dateTo01DDYY(current));
			AvaliacaoMediaMesFacade avaliacaoMediaMesFacade = (AvaliacaoMediaMesFacade)Factory.getInstance().get("avaliacaoMediaMesFacade");
			AvaliacaoMediaMes avaliacaoMediaMes = new AvaliacaoMediaMes(Integer.valueOf(idEstabelecimentoSaude), mean, current);
			avaliacaoMediaMesFacade.save(avaliacaoMediaMes);
			
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));
			
		} catch (DataIntegrityViolationException e) {
			logger.error(properties.getProperty("co.mobisaude.strings.user.notunique"), e);
			response.setErro(e.getMessage());			
			return response;
		} catch (Exception e) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), e);
			response.setErro(e.getMessage());			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/getAvaliacaoByIdESEmail")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public AvaliacaoResponse getAvaliacaoByIdESEmail(AvaliacaoRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		AvaliacaoResponse response = new AvaliacaoResponse();
		try {

			if (!request.validate()) {
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

			String idEstabelecimentoSaude = request.getIdEstabelecimentoSaude();
			String email = request.getEmail();			
			
			AvaliacaoFacade avaliacaoFacade = (AvaliacaoFacade)Factory.getInstance().get("avaliacaoFacade");
			Avaliacao avaliacao = avaliacaoFacade.getAvaliacao(Integer.valueOf(idEstabelecimentoSaude), email);
			response.setIdEstabelecimentoSaude(avaliacao.getIdEstabelecimentoSaude().toString());
			response.setEmail(avaliacao.getEmail());
			response.setTitulo(avaliacao.getTitulo());
			response.setAvaliacao(avaliacao.getAvaliacao());
			response.setRating(Float.toString(avaliacao.getRating()));
			response.setDate(sdf.format(avaliacao.getDate()));
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (DataIntegrityViolationException e) {
			logger.error(properties.getProperty("co.mobisaude.strings.user.notunique"), e);
			response.setErro(e.getMessage());			
			return response;
		} catch (Exception e) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), e);
			response.setErro(e.getMessage());			
			return response;
		}
		return response;
	}

	@POST
	@Path("/listAvaliacaoByIdES")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public AvaliacaoResponse listAvaliacaoByIdES(AvaliacaoRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		AvaliacaoResponse response = new AvaliacaoResponse();
		try {

			String token = request.getToken();
			if (!validarToken(token)) {
				logger.error(properties.getProperty("co.mobisaude.strings.geocode.tokenInvalido"));
				response.setErro(properties.getProperty("co.mobisaude.strings.geocode.tokenInvalido"));
				return response;
			}

			AvaliacaoFacade avaliacaoFacade = (AvaliacaoFacade)Factory.getInstance().get("avaliacaoFacade");
			List<Avaliacao> avaliacoes = avaliacaoFacade.listByIdEstabelecimentoSaude(Integer.valueOf(request.getIdEstabelecimentoSaude()));
			if (avaliacoes != null) {
				List<AvaliacaoDTO> avaliscoesList = new ArrayList<AvaliacaoDTO>();
				for (Avaliacao avaliacao:avaliacoes) {
					AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
					avaliacaoDTO.setIdEstabelecimentoSaude(avaliacao.getIdEstabelecimentoSaude().toString());
					avaliacaoDTO.setEmail(avaliacao.getEmail());
					avaliacaoDTO.setTitulo(avaliacao.getTitulo());
					avaliacaoDTO.setAvaliacao(avaliacao.getAvaliacao());
					avaliacaoDTO.setRating(Float.toString(avaliacao.getRating()));
					avaliacaoDTO.setDate(sdf.format(avaliacao.getDate()));										
					avaliscoesList.add(avaliacaoDTO);
				}
				response.setAvaliacoes(avaliscoesList);				
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioRegiao"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioRegiao"));				
				return response;
			}			
			
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (DataIntegrityViolationException e) {
			logger.error(properties.getProperty("co.mobisaude.strings.user.notunique"), e);
			response.setErro(e.getMessage());			
			return response;
		} catch (Exception e) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), e);
			response.setErro(e.getMessage());			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/avaliarMedia")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public AvaliacaoMediaMesResponse avaliarMedia(AvaliacaoMediaMesRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		AvaliacaoMediaMesResponse response = new AvaliacaoMediaMesResponse();
		try {

			if (!request.validate()) {
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

			String idEstabelecimentoSaude = request.getIdEstabelecimentoSaude();
			String rating = request.getRating();
			String date = request.getDate();
			
			AvaliacaoMediaMesFacade avaliacaoMediaMesFacade = (AvaliacaoMediaMesFacade)Factory.getInstance().get("avaliacaoMediaMesFacade");
			AvaliacaoMediaMes avaliacaoMediaMes;
			if(date != null){
				avaliacaoMediaMes = new AvaliacaoMediaMes(Integer.valueOf(idEstabelecimentoSaude), Float.valueOf(rating), sdf.parse(date));
			} else {
				avaliacaoMediaMes = new AvaliacaoMediaMes(Integer.valueOf(idEstabelecimentoSaude), Float.valueOf(rating));
			}
			avaliacaoMediaMesFacade.save(avaliacaoMediaMes);
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (DataIntegrityViolationException e) {
			logger.error(properties.getProperty("co.mobisaude.strings.user.notunique"), e);
			response.setErro(e.getMessage());			
			return response;
		} catch (Exception e) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), e);
			response.setErro(e.getMessage());			
			return response;
		}
		
		return response;
	}

	@POST
	@Path("/getAvaliacaoMediaByIdESDate")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public AvaliacaoMediaMesResponse getAvaliacaoMediaByIdESDate(AvaliacaoMediaMesRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		AvaliacaoMediaMesResponse response = new AvaliacaoMediaMesResponse();
		try {

			if (!request.validate()) {
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

			String idEstabelecimentoSaude = request.getIdEstabelecimentoSaude();
			String date = request.getDate();
			
			AvaliacaoMediaMesFacade avaliacaoMediaMesFacade = (AvaliacaoMediaMesFacade)Factory.getInstance().get("avaliacaoMediaMesFacade");
			AvaliacaoMediaMes avaliacaoMediaMes = avaliacaoMediaMesFacade.getByIdEstabelecimentoSaudeDate(Integer.valueOf(idEstabelecimentoSaude), sdf.parse(date));
			response.setIdEstabelecimentoSaude(avaliacaoMediaMes.getIdEstabelecimentoSaude().toString());
			response.setRating(Float.toString(avaliacaoMediaMes.getRating()));
			response.setDate(sdf.format(avaliacaoMediaMes.getDate()));
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (DataIntegrityViolationException e) {
			logger.error(properties.getProperty("co.mobisaude.strings.user.notunique"), e);
			response.setErro(e.getMessage());			
			return response;
		} catch (Exception e) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), e);
			response.setErro(e.getMessage());			
			return response;
		}
		return response;
	}

	@POST
	@Path("/listAvalicaoMediaMesByIdES")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public AvaliacaoMediaMesResponse listAvalicaoMediaMesByIdES(AvaliacaoMediaMesRequest request) {
		logger.info(new Object() {}.getClass().getEnclosingMethod().getName());	
		AvaliacaoMediaMesResponse response = new AvaliacaoMediaMesResponse();
		try {

			if (!request.validate()) {
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

			AvaliacaoMediaMesFacade avaliacaoMediaMesFacade = (AvaliacaoMediaMesFacade)Factory.getInstance().get("avaliacaoMediaMesFacade");
			List<AvaliacaoMediaMes> avaliacoes = avaliacaoMediaMesFacade.listByIdEstabelecimentoSaude(Integer.valueOf(request.getIdEstabelecimentoSaude()));
			if (avaliacoes != null) {
				List<AvaliacaoMediaMesDTO> lstRetorno = new ArrayList<AvaliacaoMediaMesDTO>();
				for (AvaliacaoMediaMes avaliacaoMediaMes:avaliacoes) {
					AvaliacaoMediaMesDTO amDTO = new AvaliacaoMediaMesDTO();
					amDTO.setIdEstabelecimentoSaude(avaliacaoMediaMes.getIdEstabelecimentoSaude().toString());
					amDTO.setRating(Float.toString(avaliacaoMediaMes.getRating()));
					amDTO.setDate(sdf.format(avaliacaoMediaMes.getDate()));										
					lstRetorno.add(amDTO);
				}
				response.setAvaliacoesMediaMes(lstRetorno);
				
			} else {
				logger.warn(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioRegiao"));
				response.setErro(properties.getProperty("co.mobisaude.strings.consultadominios.erroBuscandoDominioRegiao"));				
				return response;
			}
			
			
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (DataIntegrityViolationException e) {
			logger.error(properties.getProperty("co.mobisaude.strings.user.notunique"), e);
			response.setErro(e.getMessage());			
			return response;
		} catch (Exception e) {
			logger.error(properties.getProperty("mobisaude.strings.erroProcessandoServico"), e);
			response.setErro(e.getMessage());			
			return response;
		}
		return response;
	}
	
}