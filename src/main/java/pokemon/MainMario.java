package pokemon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MainMario {

	public static void main(String[] args) {

		HttpClient client = HttpClient.newHttpClient();
		
		// URI con la información de 1 Pokémon en concreto
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://pokeapi.co/api/v2/pokemon/")).build();
		
		ObjectMapper om = new ObjectMapper();
	}

}
