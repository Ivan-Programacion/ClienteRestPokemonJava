package pokemon;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon implements Serializable {

	// Atributos

	private String name; // Nombre pokemon
	private List<NumType> types; // tipos del pokemon (puede ser 1 tipo (size = 1) o 2 tipos (size = 2)

	// Getter y Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<NumType> getTypes() {
		return types;
	}

	public void setTypes(List<NumType> types) {
		this.types = types;
	}

	@Override
	public String toString() {
		return "Pokemon [name=" + name + ", height=" + ", types=" + types + "]";
	}

	public String tipos() {
		String resultado = "";
		for (int i = 0; i < types.size(); i++) {
			if (types.size() == 1 || i == 0)
				resultado = types.get(i).getType().getName();
			else
				resultado += " " + types.get(i).getType().getName();
		}
		return resultado;
	}

}
