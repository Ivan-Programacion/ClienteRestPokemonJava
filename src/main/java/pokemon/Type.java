package pokemon;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Type implements Serializable {

	// Atributo

	private String name; // nombre del tipo

	// Getter y Setter

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
