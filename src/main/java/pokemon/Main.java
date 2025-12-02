package pokemon;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

/* 
 * --> https://pokeapi.co/api/v2 --> API Pokemon
 * 
 * APIPOKEMON:
 * pokedex string --> url Para pokedex: https://pokeapi.co/api/v2/pokedex/
 * pokemon string --> url Para pokemon: https://pokeapi.co/api/v2/pokemon/
 * type string --> url Para tipos: https://pokeapi.co/api/v2/type/
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
 * types list NumType
 * 
 * NUMTYPE:
 * type Type
 * 
 * TYPE:
 * name string
 * 
 * LISTA DE TIPOS ---> https://pokeapi.co/api/v2/type/
 * 
 * TYPEAUXILIAR
 * name string
 * url string
 */

public class Main {

	static HashMap<String, Object> cache = new HashMap<>(); // caché de los end points
	static String api = "https://pokeapi.co/api/v2"; // Api general
	// Jackson
	static ObjectMapper mapper = new ObjectMapper();
	static HttpClient cliente = HttpClient.newHttpClient();
	static HttpRequest peticion;
	static HttpResponse<String> respuesta;
	// Scanner
	static Scanner sc = new Scanner(System.in);
	// Datos necesarios
	static String json;
	static String url;
	static final int NUM_POKEMON_ADIVINAR = 10;
	// listaPokemon -> guardaremos los 10 pokemon aleatorios a adivinar
	static ArrayList<Pokemon> listaPokemon;
	// listaTipos -> guardaremos todos los tipos sacados de la API
	static ArrayList<String> listaTipos = new ArrayList<>();
	// listaUsuarios --> Guardaremos la lista de usuarios cargados desde un fichero
	// JSON
	static ListaUsuario listaUsuarios = null;
	// miUsuario --> usuario actual jugando
	static Usuario miUsuario;
	// fichero --> Fichero JSON donde guardaremos los datos del usuario (name,
	// score)
	static File fichero;

	public static void main(String[] args) {
		// Cargamos los usuarios en la listaUsuarios
		cargarUsuarios();
		int opcion = -1;
		while (opcion != 0) {
			opcion = -1;
			try {
				// Cogemos los 10 pokemon aleatorios y los guardamos en la listaPokemon
				listaPokemon = new ArrayList<>();
				for (int i = 0; i < NUM_POKEMON_ADIVINAR; i++) {
					Pokemon pokemon = pokemon();
					// Si el pokemon == null => no lo añadas a la lista
					if (pokemon.getName() != null)
						listaPokemon.add(pokemon);
					else
						i -= 1;
				}
				// Cargamos todos los tipos en la listaTipos
				cargarTipos();
				// Arrancamos el juego
				adivinarTipos();
				while (opcion != 1 && opcion != 0) {
					try {
						System.out.println("¿Quieres volver a jugar? (1 -> Sí | 0 -> No)");
						opcion = Integer.parseInt(sc.nextLine());
						if (opcion < 0 || opcion > 1)
							System.out.println("Escribe un número correcto");
					} catch (NumberFormatException n) {
						System.out.println("Escribe un número");
					}

				}
				// Excepción de fallo de conexión
			} catch (ConnectException x) {
				System.out.println("No se ha podido conectar al juego. Intentalo más tarde");
				System.exit(-1);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		System.out.println("¡Gracias por jugar " + miUsuario.getUser() + "! ¡Hasta la próxima!");
		sc.close();
		cliente.close();
	}

	private static void cargarUsuarios() {
		// Creamos el fichero o lo cargamos si ya existiera
		String usuarios = "usuarios.json";
		fichero = new File(usuarios);
		try {
			if (!fichero.exists()) {
				fichero.createNewFile();
				listaUsuarios = new ListaUsuario(new ArrayList<>());
			} else {
				listaUsuarios = mapper.readValue(fichero, ListaUsuario.class);
			}
			// Pedimos al usuario su nombre
			System.out.println("Escribe tu nombre de usuario:");
			String nombre = sc.nextLine();
			miUsuario = null;
			for (Usuario usuario : listaUsuarios.getListaUsuarios()) {
				// Si existe, cargamos sus datos
				if (usuario.getUser().equals(nombre))
					miUsuario = usuario;
			}
			// Si no existe, añadimos un nuevo registro al JSON
			if (miUsuario == null) {
				miUsuario = new Usuario(nombre, 0);
				listaUsuarios.getListaUsuarios().add(miUsuario);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static void cargarTipos() throws IOException, InterruptedException {
		// ----------- APITYPE -----------
		ApiType miApiType = new ApiType();
		ApiPokemon miApiPokemon = (ApiPokemon) cache.get(api);
		url = miApiPokemon.getType();
		if (!cache.containsKey(url)) {
			peticion = HttpRequest.newBuilder().uri(URI.create(url)).build();
			respuesta = cliente.send(peticion, BodyHandlers.ofString());
			json = respuesta.body();
			miApiType = mapper.readValue(json, ApiType.class);
			cache.put(url, miApiType);

		} else {
			miApiType = (ApiType) cache.get(url);
		}
		// Si no estuviera vacía, utilizamos la de la caché.
		if (listaTipos.isEmpty()) {
			for (TypeAuxiliar resultado : miApiType.getResults()) {
				// No añadimos los tipos: unknown y stellar porque no pertenecen a ningún
				// pokemon (no existen o son error de la api)
				if (!resultado.getName().equals("unknown") && !resultado.getName().equals("stellar"))
					listaTipos.add(resultado.getName());
			}
		}
	}

	// JUEGO
	private static void adivinarTipos() {
		int puntos = 0;
		int contador = 1;
		Random random = new Random();

		// Bienvenida
		System.out.println("¡Bienvenido, " + miUsuario.getUser() + ", al juego de adivinar los tipos de los Pokémon!");
		System.out.println("Constará de 10 preguntas y cada acierto serán 10 puntos. ¡Buena suerte!");
		System.out.println("Tu mayor score registrado es: " + miUsuario.getScore());
		System.out.println();
		for (Pokemon pokemon : listaPokemon) {
			System.out.println("Pregunta " + contador++ + " | -> Score: " + puntos);
			System.out.println("- Pokemon: " + pokemon.getName()); // + "\n- Tipo: " + pokemon.tipos());
			System.out.println();

			// Tipos correctos (puede tener 1 o 2 tipos)
			ArrayList<String> tiposCorrectos = new ArrayList<>();
			for (NumType nt : pokemon.getTypes()) {

				tiposCorrectos.add(nt.getType().getName()); // Extraemos el nombre del tipo

			}

			// Construimos la opción correcta (combinada si hay 2 tipos)
			String opcionCorrecta;
			if (tiposCorrectos.size() == 2) {
				opcionCorrecta = tiposCorrectos.get(0) + "/" + tiposCorrectos.get(1);
			} else {
				opcionCorrecta = tiposCorrectos.get(0);
			}

			// Lista que tendrá las 4 opciones, una de ellas siendo la correcta
			ArrayList<String> listaOpciones = new ArrayList<>();
			listaOpciones.add(opcionCorrecta);

			while (listaOpciones.size() < 4) {
				String candidato;
				// Contemplamos las 2 opciones: 1 tipo o 2 tipos
				if (random.nextBoolean()) {
					// 1 tipo
					candidato = listaTipos.get(random.nextInt(listaTipos.size()));
				} else {
					// 2 tipos distintos
					String tipoA = listaTipos.get(random.nextInt(listaTipos.size()));
					String tipoB = listaTipos.get(random.nextInt(listaTipos.size()));
					while (tipoB.equals(tipoA)) {
						tipoB = listaTipos.get(random.nextInt(listaTipos.size()));
					}
					candidato = tipoA + "/" + tipoB;
				}

				// Evitamos duplicados y que coincida con la opción correcta
				if (!listaOpciones.contains(candidato)) {
					listaOpciones.add(candidato);
				}
			}

			// Desordenamos las opciones
			java.util.Collections.shuffle(listaOpciones);

			// Las mostramos
			for (int i = 0; i < listaOpciones.size(); i++) {
				System.out.println((i + 1) + ". " + listaOpciones.get(i));
			}
			// Elección de opción
			int opcionFinal = 0;
			while (opcionFinal < 1 || opcionFinal > 4) {
				System.out.println("Elige una opción (1-4): ");
				String opcion = sc.nextLine();
				try {
					opcionFinal = Integer.parseInt(opcion);

					// Comprobamos que introduce un número adecuado
					if (opcionFinal < 1 || opcionFinal > 4) {
						System.out.println("Escribe un número correcto");
					} else {
						String opcionElegida = listaOpciones.get(opcionFinal - 1);
						// Si la respuesta coincide con la opción correcta habrá acertado
						if (opcionElegida.equals(opcionCorrecta)) {
							System.out.println("¡Correcto! +10 Puntos");
							puntos += 10;

						} else {
							System.out.println("Incorrecto. El tipo correcto era: " + opcionCorrecta);
						}
					}
					// Captamos la excepción en caso de que ponga una opción que no sea numérica
				} catch (NumberFormatException e) {
					System.out.println("Escribe un número");
				}
			}

			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------");
		}

		// RESULTADO
		System.out.println("Fin del juego. Score total conseguido: " + puntos + " puntos.");
		if (puntos > miUsuario.getScore()) {
			miUsuario.setScore(puntos);
			for (Usuario usuario : listaUsuarios.getListaUsuarios()) {
				if (usuario.getUser().equals(miUsuario.getUser())) {
					usuario.setScore(puntos);
				}
			}
		}
		System.out.println("Tu mayor score registrado es: " + miUsuario.getScore());
		// Actualizamos la puntuación del usuario en caso de que haya superado su
		// anterior score
		try {
			mapper.writeValue(fichero, listaUsuarios);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println();

	}

	private static Pokemon pokemon() throws IOException, InterruptedException {
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
		try {
			if (!cache.containsKey(url)) {
				peticion = HttpRequest.newBuilder().uri(URI.create(url)).build();
				respuesta = cliente.send(peticion, BodyHandlers.ofString());
				json = respuesta.body();
				miPokemon = mapper.readValue(json, Pokemon.class);
				cache.put(url, miPokedex);
			} else
				miPokemon = (Pokemon) cache.get(url);
			// Tratamos excepcion JsonParseException para que, en caso de que la estructura
			// del json que nos de del pokemon no sea correcta, no rompa el programa
			// igualmente
		} catch (JsonParseException e) {
//			System.err.println("ERROR");
//			e.printStackTrace();
		}
		return miPokemon;
	}

}
