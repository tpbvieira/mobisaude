package co.salutary.mobisaude.model;

import android.util.Log;

import org.json.JSONObject;

public class Erb {

    private static final String TAG = Erb.class.getSimpleName();

	private long id;
	private int idCidade;
	private Double latitudeStel;
	private Double longitudeStel;
	private String nomeFantasia;
	private String prestadora;
	private boolean tecnologia2g;
	private boolean tecnologia3g;
	private boolean tecnologia4g;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(int idCidade) {
		this.idCidade = idCidade;
	}
	public Double getLatitudeStel() {
		return latitudeStel;
	}
	public void setLatitudeStel(Double latitudeStel) {
		this.latitudeStel = latitudeStel;
	}
	public Double getLongitudeStel() {
		return longitudeStel;
	}
	public void setLongitudeStel(Double longitudeStel) {
		this.longitudeStel = longitudeStel;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getPrestadora() {
		return prestadora;
	}
	public void setPrestadora(String prestadora) {
		this.prestadora = prestadora;
	}
	public boolean isTecnologia2g() {
		return tecnologia2g;
	}
	public void setTecnologia2g(boolean tecnologia2g) {
		this.tecnologia2g = tecnologia2g;
	}
	public boolean isTecnologia3g() {
		return tecnologia3g;
	}
	public void setTecnologia3g(boolean tecnologia3g) {
		this.tecnologia3g = tecnologia3g;
	}
	public boolean isTecnologia4g() {
		return tecnologia4g;
	}
	public void setTecnologia4g(boolean tecnologia4g) {
		this.tecnologia4g = tecnologia4g;
	}

    public static Erb jsonObjectToErb(JSONObject rec){
        try {
            Erb erb = new Erb();
            erb.setIdCidade(rec.getInt("codMunicipioIbge"));
            erb.setLatitudeStel(rec.getDouble("latitudeStel"));
            erb.setLongitudeStel(rec.getDouble("longitudeStel"));
            erb.setNomeFantasia(rec.getString("nomeFantasia"));
            erb.setPrestadora(rec.getString("prestadora"));
            erb.setTecnologia2g(rec.getBoolean("tecnologia2g"));
            erb.setTecnologia3g(rec.getBoolean("tecnologia3g"));
            erb.setTecnologia4g(rec.getBoolean("tecnologia4g"));
            return erb;
        } catch (Exception e) {
            Log.d(TAG, "UtilError converting from json", e);
            return null;
        }
    }
}