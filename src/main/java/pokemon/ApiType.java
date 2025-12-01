package pokemon;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiType implements Serializable {

	private ArrayList<TypeAuxiliar> results;

	public ArrayList<TypeAuxiliar> getResults() {
		return results;
	}

	public void setResults(ArrayList<TypeAuxiliar> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ApiType [results=" + results + "]";
	}
}
