package pokemon;

import java.util.HashMap;

/* 
 * --> https://pokeapi.co/api/v2 --> API Pokemon
 * 
 * APIPOKEMON:
 * pokedex string --> url Para pokedex: https://pokeapi.co/api/v2/pokedex/
 * pokemon string --> url Para pokemon: https://pokeapi.co/api/v2/pokemon/
 * 
 * APIPOKEDEX - https://pokeapi.co/api/v2/pokedex/
 * results list PokedexAuxiliar
 * 
 * POKEDEXAUXILIAR
 * url string --> https://pokeapi.co/api/v2/pokedex/?/ -- ? = numero pokedex
 * 
 * POKEDEX: https://pokeapi.co/api/v2/pokedex/?/
 * name string
 * pokemon_entries list PokemonAuxiliar
 * 
 * PARA LA LISTA:
 * PokemonAuxiliar.entry_number --> numero pokedex del pokemon
 * PokemonAuxiliar.pokemon_species.name --> nombre de pokemon
 * 
 * POKEMONAUXILIAR:
 * entry_number int
 * pokemon_species PokemonSpecies
 * 
 * POKEMONSPECIES
 * name string
 * 
 * ---> ApiPokemon.getPokemon() https://pokeapi.co/api/v2/pokemon/[name] --> datos pokemon
 * 
 * POKEMON: https://pokeapi.co/api/v2/pokemon/[name]
 * name string
 * height int
 * weight int
 * types list NumType
 * 
 * NUMTYPE:
 * type Type
 * 
 * TYPE:
 * name string
 */

public class Main {

	public static void main(String[] args) {

		HashMap<String, Object> cache; // caché de los end points
		
	}

}
