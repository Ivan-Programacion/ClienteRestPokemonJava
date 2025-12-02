package pokemon;

public class Usuario {
	
	private String user; // nombre usuario
	private int score; // puntación máxima conseguida

	public Usuario() {

	}

	public Usuario(String user, int score) {
		this.user = user;
		this.score = score;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Usuario [user=" + user + ", score=" + score + "]";
	}
}
