package co.salutary.mobisaude.model.user;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"tb_user\"", schema = "public")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349167L;
	private String email;
	private String password;
	private String name;
	private String phone;

	public User(String email, String password, String name, String phone) {
		this.email = email;
		this.password = password;		
		this.name = name;
		this.phone = phone;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Id
	@Column(name = "\"tx_email\"", unique = true, nullable = false, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "\"tx_password\"", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "\"tx_name\"", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "\"tx_phone\"", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}