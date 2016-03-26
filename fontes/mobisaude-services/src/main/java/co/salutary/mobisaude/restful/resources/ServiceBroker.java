package co.salutary.mobisaude.restful.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import co.salutary.mobisaude.model.Factory;
import co.salutary.mobisaude.model.estabelecimentosaude.EstabelecimentoSaude;
import co.salutary.mobisaude.model.estabelecimentosaude.facade.EstabelecimentoSaudeFacade;
import co.salutary.mobisaude.model.municipio.facade.MunicipiosIbgeFacade;
import co.salutary.mobisaude.model.regiao.Regiao;
import co.salutary.mobisaude.model.regiao.facade.RegiaoFacade;
import co.salutary.mobisaude.model.tipoestabelecimentosaude.facade.TipoEstabelecimentoSaudeFacade;
import co.salutary.mobisaude.model.tipogestao.facade.TipoGestaoFacade;
import co.salutary.mobisaude.model.tiposistemaoperacional.facade.TipoSistemaOperacionalFacade;
import co.salutary.mobisaude.model.user.User;
import co.salutary.mobisaude.model.user.facade.UserFacade;
import co.salutary.mobisaude.restful.message.mobile.ESMsg;
import co.salutary.mobisaude.restful.message.mobile.RegiaoMsg;
import co.salutary.mobisaude.restful.message.mobile.TipoEstabelecimentoSaudeMsg;
import co.salutary.mobisaude.restful.message.mobile.TipoGestaoMsg;
import co.salutary.mobisaude.restful.message.mobile.TipoSistemaOperacional;
import co.salutary.mobisaude.restful.message.request.ConsultaDominiosRequest;
import co.salutary.mobisaude.restful.message.request.GeocodeRequest;
import co.salutary.mobisaude.restful.message.request.GerarChaveRequest;
import co.salutary.mobisaude.restful.message.request.GerarTokenRequest;
import co.salutary.mobisaude.restful.message.request.GetESRequest;
import co.salutary.mobisaude.restful.message.request.UserRequest;
import co.salutary.mobisaude.restful.message.response.ConsultaDominiosResponse;
import co.salutary.mobisaude.restful.message.response.GeocodeResponse;
import co.salutary.mobisaude.restful.message.response.GerarChaveResponse;
import co.salutary.mobisaude.restful.message.response.GerarTokenResponse;
import co.salutary.mobisaude.restful.message.response.GetESResponse;
import co.salutary.mobisaude.restful.message.response.UserResponse;

@Path("/mobile")
@Controller
public class ServiceBroker extends AbstractServiceBroker {

	private static final Log logger = LogFactory.getLog(ServiceBroker.class);

	public ServiceBroker() {

	}

	@POST
	@Path("/gerarToken")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GerarTokenResponse gerarToken(GerarTokenRequest request) {logger.debug(new Object() {}.getClass().getEnclosingMethod().getName());	
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

	@POST
	@Path("/gerarChave")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GerarChaveResponse gerarChave(GerarChaveRequest request) {logger.debug(new Object() {}.getClass().getEnclosingMethod().getName());
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
	@Path("/geocode")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public GeocodeResponse geocode(GeocodeRequest request) {logger.debug(new Object() {}.getClass().getEnclosingMethod().getName());
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

	@POST
	@Path("/consultaDominios")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public ConsultaDominiosResponse consultaDominios(ConsultaDominiosRequest request) {
		logger.debug(new Object() {}.getClass().getEnclosingMethod().getName());
		
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
				response.setTiposGestao((lstRetorno.toArray(new TipoGestaoMsg[0])));
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
	
	@POST
	@Path("/signup")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public UserResponse signup(UserRequest request) {
		logger.debug(new Object() {}.getClass().getEnclosingMethod().getName());	
		UserResponse response = new UserResponse();
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

			String email = request.getEmail();
			String password = request.getPassword();
			String name = request.getName();
			String phone = request.getPhone();
			User newUser = new User(email,password,name,phone);

			UserFacade userFacade = (UserFacade)Factory.getInstance().get("userFacade");
			userFacade.save(newUser);
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.strings.geocode.erroProcessandoServico"), ex);
			response.setErro(ex.getMessage());			
			return response;
		}
		return response;
	}
	
	@POST
	@Path("/updateUser")
	@Consumes("application/json;charset=utf-8")
	@Produces("application/json;charset=utf-8")
	public UserResponse updateUser(UserRequest request) {
		logger.debug(new Object() {}.getClass().getEnclosingMethod().getName());	
		UserResponse response = new UserResponse();
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

			String email = request.getEmail();
			String password = request.getPassword();
			String name = request.getName();
			String phone = request.getPhone();
			User newUser = new User(email,password,name,phone);

			UserFacade userFacade = (UserFacade)Factory.getInstance().get("userFacade");
			newUser = userFacade.update(newUser);
			response.setEmail(newUser.getEmail());
			response.setPassword(newUser.getPassword());
			response.setName(newUser.getName());
			response.setPhone(newUser.getPhone());
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.strings.geocode.erroProcessandoServico"), ex);
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
		logger.debug(new Object() {}.getClass().getEnclosingMethod().getName());	
		UserResponse response = new UserResponse();
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

			UserFacade userFacade = (UserFacade)Factory.getInstance().get("userFacade");
			User newUser = userFacade.get(request.getEmail());
			response.setEmail(newUser.getEmail());
			response.setPassword(newUser.getPassword());
			response.setName(newUser.getName());
			response.setPhone(newUser.getPhone());
			response.setErro(properties.getProperty("co.mobisaude.strings.sucesso"));

		} catch (Exception ex) {
			logger.error(properties.getProperty("co.mobisaude.strings.geocode.erroProcessandoServico"), ex);
			response.setErro(ex.getMessage());			
			return response;
		}
		return response;
	}
	
}