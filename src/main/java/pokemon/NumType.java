package pokemon;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NumType implements Serializable {

	// Atributo

	private Type type;

	// Getter y Setter

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
