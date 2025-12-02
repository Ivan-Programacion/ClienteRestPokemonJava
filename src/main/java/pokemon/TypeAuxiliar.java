package pokemon;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeAuxiliar implements Serializable {

	private String name; // nombre del tipo
	private String url; // más datos sobre el tipo

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "TypeAuxiliar [name=" + name + ", url=" + url + "]";
	}

}
