package pokemon;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokedexAuxiliar implements Serializable {

	private String url; // url de pokedex

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "PokedexAuxiliar [url=" + url + "]";
	}
}
