package co.salutary.mobisaude.model.tipogestao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"tb_tipo_gestao\"", schema = "public")
public class TipoGestao implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349167L;
	private short idTipoGestao;
	private String nome;

	public TipoGestao() {
	}

	public TipoGestao(short idTipoGestao) {
		this.idTipoGestao = idTipoGestao;
	}

	@Id
	@Column(name = "\"nu_id_tipo_gestao\"", unique = true, nullable = false)
	public short getIdTipoGestao() {
		return this.idTipoGestao;
	}

	public void setIdTipoGestao(short idTipoGestao) {
		this.idTipoGestao = idTipoGestao;
	}

	@Column(name = "\"tx_nome\"")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}