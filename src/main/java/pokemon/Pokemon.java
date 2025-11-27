package pokemon;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon implements Serializable {

	// Atributos

	private String name;
	private int height;
	private int weight;
	private List<NumType> types;

	// Getter y Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public List<NumType> getTypes() {
		return types;
	}

	public void setTypes(List<NumType> types) {
		this.types = types;
	}

	@Override
	public String toString() {
		return "Pokemon [name=" + name + ", height=" + height + ", weight=" + weight + ", types=" + types + "]";
	}

	public String tipos() {
		String resultado = "";
		for (int i = 0; i < types.size(); i++) {
			if(types.size() == 1 || i == 0)
				resultado = types.get(i).getType().getName();
			else
				resultado += " " + types.get(i).getType().getName();
		}
		return resultado;
	}

}
