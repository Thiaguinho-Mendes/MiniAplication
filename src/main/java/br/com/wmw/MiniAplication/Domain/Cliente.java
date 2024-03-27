package br.com.wmw.MiniAplication.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	private String nome;

	@Column
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;

	private String cpfCnpj;
	private String telefone;
	private String email;

	public Cliente() {}
	
	public Cliente(String nome, TipoPessoa tipoPessoa, String cpfCnpj, String telefone, String email) {
		this.nome = nome;
		this.tipoPessoa = tipoPessoa;
		this.cpfCnpj = cpfCnpj;
		this.telefone = telefone;
		this.email = email;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
