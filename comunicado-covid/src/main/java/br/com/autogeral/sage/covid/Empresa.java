package br.com.autogeral.sage.covid;

public class Empresa {

	private int codigo;
	private String nome;
	private String cnpj;
	private String endereco;
	private String cidade;
	private String representanteNome;
	private String representanteCpf;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getRepresentanteNome() {
		return representanteNome;
	}

	public void setRepresentanteNome(String representanteNome) {
		this.representanteNome = representanteNome;
	}

	public String getRepresentanteCpf() {
		return representanteCpf;
	}

	public void setRepresentanteCpf(String representanteCpf) {
		this.representanteCpf = representanteCpf;
	}

	@Override
	public String toString() {
		return "Empresa [codigo=" + codigo + ", nome=" + nome + ", cnpj=" + cnpj + ", endereco=" + endereco
				+ ", cidade=" + cidade + ", representanteNome=" + representanteNome + ", representanteCpf="
				+ representanteCpf + "]";
	}
	
	

}
