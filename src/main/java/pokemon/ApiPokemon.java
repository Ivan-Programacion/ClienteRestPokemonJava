package pokemon;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPokemon implements Serializable {

	private String pokedex; // url pokedex
	private String pokemon; // url pokemon

	public String getPokedex() {
		return pokedex;
	}

	public void setPokedex(String pokedex) {
		this.pokedex = pokedex;
	}

	public String getPokemon() {
		return pokemon;
	}

	public void setPokemon(String pokemon) {
		this.pokemon = pokemon;
	}

	@Override
	public String toString() {
		return "ApiPokemon [pokedex=" + pokedex + ", pokemon=" + pokemon + "]";
	}
}
