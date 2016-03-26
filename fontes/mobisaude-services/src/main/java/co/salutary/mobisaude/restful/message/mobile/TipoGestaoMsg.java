package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "tipoGestao")
public class TipoGestaoMsg implements Cloneable {

	private String id;
	private String nome;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
	    TipoGestaoMsg cloned = (TipoGestaoMsg)super.clone();
	    return cloned;
	}
	
}