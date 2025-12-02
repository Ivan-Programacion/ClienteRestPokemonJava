package pokemon;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonSpecies implements Serializable {
	
	// Nombre pokemon --> se utilizará para acceder a un endpoint con más datos del pokemon
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PokemonSpecies [name=" + name + "]";
	}
}
