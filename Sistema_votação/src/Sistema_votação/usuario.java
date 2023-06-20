package Sistema_votação;

public class usuario {
	private int idUsuario;
	private String nome;
	private String perfil;
	
	public usuario(int idUsuario, String nome, String perfil) {
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.perfil = perfil;	
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	
}
