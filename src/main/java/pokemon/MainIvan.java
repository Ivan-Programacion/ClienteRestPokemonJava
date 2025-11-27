package pokemon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MainIvan {

	static HashMap<String, Object> cache = new HashMap<>(); // caché de los end points
	static String api = "https://pokeapi.co/api/v2";
	static ObjectMapper mapper = new ObjectMapper();
	static HttpClient cliente = HttpClient.newHttpClient();
	static HttpRequest peticion;
	static HttpResponse<String> respuesta;
	static Scanner sc = new Scanner(System.in);
	static String json;
	static ApiPokemon miApi;
	
	public static void main(String[] args) {
		
		try {
			datosApi();
			if (!cache.containsKey(api)) {
				peticion = HttpRequest.newBuilder().uri(URI.create(api)).build();
				respuesta = cliente.send(peticion, BodyHandlers.ofString());
				// Guardamos el JSON que nos ha dado la api
				json = respuesta.body();
				// Creamos objeto principal y trabajamos con él
				miApi = new ApiPokemon();
				miApi = mapper.readValue(json, ApiPokemon.class);
				cache.put(api, miApi);
			} else {
				miApi = (ApiPokemon) cache.get(api);
			}
		} catch (Exception e) {
			
		}
	}

	private static void datosApi() {
		
	}

}
