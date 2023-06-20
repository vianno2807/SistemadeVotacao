package Sistema_votação;

public class eleicao {

	private Integer idEleicao;
	private String nomeEleicao;	
	
	public eleicao(Integer idEleicao, String nomeEleicao) {
		this.idEleicao = idEleicao;
		this.nomeEleicao = nomeEleicao;
	}
	
	public Integer getIdEleicao() {
		return idEleicao;
	}
	public void setIdEleicao(Integer idEleicao) {
		this.idEleicao = idEleicao;
	}
	public String getNomeEleicao() {
		return nomeEleicao;
	}
	public void setNomeEleicao(String nomeEleicao) {
		this.nomeEleicao = nomeEleicao;
	}
	
	
}
