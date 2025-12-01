package pokemon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MainIvan {

	static ObjectMapper mapper = new ObjectMapper();
	static Scanner sc = new Scanner(System.in);
	static String json;

	public static void main(String[] args) {
		ListaUsuario listaUsuarios = null;
		String usuarios = "usuarios.json";
		File fichero = new File(usuarios);
		try {
			if (!fichero.exists()) {
				fichero.createNewFile();
				listaUsuarios = new ListaUsuario(new ArrayList<>());
			} else {
				listaUsuarios = mapper.readValue(fichero, ListaUsuario.class);
			}

		System.out.println("Escribe tu nombre de usuario:");
		String nombre = sc.nextLine();
		Usuario miUsuario = null;
		for (Usuario usuario : listaUsuarios.getListaUsuarios()) {
			if (usuario.getUser().equals(nombre))
				miUsuario = usuario;
		}
		if (miUsuario == null) {
			miUsuario = new Usuario(nombre, 0);
			listaUsuarios.getListaUsuarios().add(miUsuario);
		}
		// Cuando se cierre el programa
		mapper.writeValue(fichero, listaUsuarios);
		System.out.println("Bienvenido, " + miUsuario.getUser());
		System.out.println("Tu score es: " + miUsuario.getScore());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
