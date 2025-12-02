package pokemon;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonAuxiliar implements Serializable {

	private Integer entry_number; // ID pokedex del pokemon
	private PokemonSpecies pokemon_species; // Datos del pokemon

	public Integer getEntry_number() {
		return entry_number;
	}

	public void setEntry_number(Integer entry_number) {
		this.entry_number = entry_number;
	}

	public PokemonSpecies getPokemon_species() {
		return pokemon_species;
	}

	public void setPokemon_species(PokemonSpecies pokemon_species) {
		this.pokemon_species = pokemon_species;
	}

	@Override
	public String toString() {
		return "PokemonAuxiliar [entry_number=" + entry_number + ", pokemon_species=" + pokemon_species + "]";
	}

}
