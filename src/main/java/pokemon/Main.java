package pokemon;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/* 
 * --> https://pokeapi.co/api/v2 --> API Pokemon
 * 
 * APIPOKEMON:
 * pokedex string --> url Para pokedex: https://pokeapi.co/api/v2/pokedex/
 * pokemon string --> url Para pokemon: https://pokeapi.co/api/v2/pokemon/
 * 
 * APIPOKEDEX - https://pokeapi.co/api/v2/pokedex/
 * results list PokedexAuxiliar --> get(0) --> Pokedex nacional con TODOS LOS POKEMON
 * 
 * POKEDEXAUXILIAR
 * url string --> https://pokeapi.co/api/v2/pokedex/1 --> numero pokedex
 * 
 * POKEDEX: https://pokeapi.co/api/v2/pokedex/1
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
	
	static HashMap<String, Object> cache = new HashMap<>(); // caché de los end points
	static String api = "https://pokeapi.co/api/v2";
	static ObjectMapper mapper = new ObjectMapper();
	static HttpClient cliente = HttpClient.newHttpClient();
	static HttpRequest peticion;
	static HttpResponse<String> respuesta;
	static Scanner sc = new Scanner(System.in);
	static String json;
	static String url;

	public static void main(String[] args) {

		try {
			apiRest();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

	private static void apiRest()
			throws IOException, InterruptedException, JsonProcessingException, JsonMappingException {
		// ----------- APIPOKEMON -----------
		ApiPokemon miApi = new ApiPokemon();
		url = api;
		if (!cache.containsKey(url)) {
			peticion = HttpRequest.newBuilder().uri(URI.create(url)).build();
			respuesta = cliente.send(peticion, BodyHandlers.ofString());
			json = respuesta.body();
			miApi = mapper.readValue(json, ApiPokemon.class);
			cache.put(api, miApi);
		} else
			miApi = (ApiPokemon) cache.get(url);
		// ----------- APIPOKEDEX -----------
		ApiPokedex miApiPokedex = new ApiPokedex();
		url = miApi.getPokedex();
		if (!cache.containsKey(url)) {
			peticion = HttpRequest.newBuilder().uri(URI.create(url)).build();
			respuesta = cliente.send(peticion, BodyHandlers.ofString());
			json = respuesta.body();
			miApiPokedex = mapper.readValue(json, ApiPokedex.class);
			cache.put(url, miApiPokedex);
		} else
			miApiPokedex = (ApiPokedex) cache.get(url);
		// AGREGAMOS POKEDEX NACIONAL
		// ----------- POKEDEX -----------
		Pokedex miPokedex = new Pokedex();
		url = miApiPokedex.getResults().get(0).getUrl(); // URL POKEDEX NACIONAL
		if (!cache.containsKey(url)) {
			peticion = HttpRequest.newBuilder().uri(URI.create(url)).build();
			respuesta = cliente.send(peticion, BodyHandlers.ofString());
			json = respuesta.body();
			miPokedex = mapper.readValue(json, Pokedex.class);
			cache.put(url, miPokedex);
		} else
			miPokedex = (Pokedex) cache.get(url);

		// POKEMON ALEATORIO AL AZAR
		Random idRandom = new Random();
		int id = idRandom.nextInt(miPokedex.getPokemon_entries().size()); // Cogemos un pokemon al azar
		String nombrePokemon = miPokedex.getPokemon_entries().get(id).getPokemon_species().getName(); // Cogemos su
																										// nombre
		// ----------- POKEMON -----------
		Pokemon miPokemon = new Pokemon();
		url = miApi.getPokemon() + nombrePokemon; // ENDPOINT PARA DATOS DEL POKEMON -->
													// https://pokeapi.co/api/v2/pokemon/[nombrePokemon]
		if (!cache.containsKey(url)) {
			peticion = HttpRequest.newBuilder().uri(URI.create(url)).build();
			respuesta = cliente.send(peticion, BodyHandlers.ofString());
			json = respuesta.body();
			miPokemon = mapper.readValue(json, Pokemon.class);
			cache.put(url, miPokedex);
		} else
			miPokemon = (Pokemon) cache.get(url);

		// PRUEBA
		System.out.println(miPokemon.toString());
	}

}
