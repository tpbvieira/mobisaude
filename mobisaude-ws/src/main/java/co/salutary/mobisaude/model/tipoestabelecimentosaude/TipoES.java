package co.salutary.mobisaude.model.tipoestabelecimentosaude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"tb_tipo_estabelecimento_saude\"", schema = "public")
public class TipoES implements java.io.Serializable {
	
	private static final long serialVersionUID = 5949891975169349167L;
	private int idTipoES;
	private String nome;

	public TipoES() {
	}

	public TipoES(int idTipoES) {
		this.idTipoES = idTipoES;
	}

	@Id
	@Column(name = "\"nu_id_tipo_estabelecimento_saude\"", unique = true, nullable = false)
	public int getIdTipoES() {
		return this.idTipoES;
	}

	public void setIdTipoES(int idTipoES) {
		this.idTipoES = idTipoES;
	}

	@Column(name = "\"tx_nome\"")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}