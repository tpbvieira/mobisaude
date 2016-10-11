package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "postMessageRequest")
public class PostMessageRequest implements IMobileRequest {

	private String uid;
	private String pass;
	
	public PostMessageRequest(){
		uid = null;
		pass = null;			
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	@Override
	public boolean validate() {
		if (uid == null || uid.trim().equals("")) {
			return false;
		}
		
		if (pass == null || pass.trim().equals("")) {
			return false;
		}
		
		return true;
	}

}