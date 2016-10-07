package co.salutary.mobisaude.restful.message.request;

import java.util.StringTokenizer;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.salutary.mobisaude.exception.MobisaudeServicesException;

@XmlRootElement
@XmlType(namespace = "mobile", name = "userRequest")
public class UserRequest implements IMobileRequest {

	private static final Log logger = LogFactory.getLog(UserRequest.class);

	private String token;
	private String email;
	private String password;
	private String name;
	private String phone;
	private boolean contactable;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

	public boolean isContactable() {
		return contactable;
	}

	public void setContactable(boolean contactable) {
		this.contactable = contactable;
	}

	@Override
	public boolean validate() {
		boolean result = false;

        try{
            if(email.contains("@") && email.contains(".")){
                StringTokenizer tokens = new StringTokenizer(email,"@");
                tokens.nextToken();
                String domainValue = tokens.nextToken();
                tokens = new StringTokenizer(domainValue,".");                
                if(tokens.countTokens() > 1) {
                    result = true;
                }else{            	
                	throw new MobisaudeServicesException("Email Inválido");
                }
            }else{  	
            	throw new MobisaudeServicesException("Email Inválido");
            }
        }catch(Exception e){
            logger.error(e);
        }

        return result;
	}

}