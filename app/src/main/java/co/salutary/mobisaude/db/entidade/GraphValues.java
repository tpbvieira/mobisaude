package co.salutary.mobisaude.db.entidade;

import java.util.List;

public class GraphValues {
	
	List<Double> values;
	String date;
	
	public void setDate(String date) {
		this.date = date;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}
	
	public List<Double> getValues() {
		return values;
	}
	
	public String getDate() {
		return date;
	}
}
