package pokemon;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokedex implements Serializable {

	private String name; // nombre pokedex
	private ArrayList<PokemonAuxiliar> pokemon_entries; // datos del pokemon

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<PokemonAuxiliar> getPokemon_entries() {
		return pokemon_entries;
	}

	public void setPokemon_entries(ArrayList<PokemonAuxiliar> pokemon_entries) {
		this.pokemon_entries = pokemon_entries;
	}

	@Override
	public String toString() {
		return "Pokedex [name=" + name + ", listaAuxiliar=" + pokemon_entries + "]";
	}
}
