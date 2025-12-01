package pokemon;

import java.util.ArrayList;

public class ListaUsuario {

	private ArrayList<Usuario> listaUsuarios;

	public ListaUsuario() {

	}

	public ListaUsuario(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
}
