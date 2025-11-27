package pokemon;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPokedex implements Serializable {

	private ArrayList<PokedexAuxiliar> results;

	public ArrayList<PokedexAuxiliar> getResults() {
		return results;
	}

	public void setResults(ArrayList<PokedexAuxiliar> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ApiPokedex [results=" + results + "]";
	}
}
