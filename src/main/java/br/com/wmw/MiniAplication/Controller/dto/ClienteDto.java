package br.com.wmw.MiniAplication.Controller.dto;


import java.util.List;

import br.com.wmw.MiniAplication.Controller.Validate.CpfOrCnpj;
import br.com.wmw.MiniAplication.Domain.Cliente;
import br.com.wmw.MiniAplication.Domain.TipoPessoa;
import br.com.wmw.MiniAplication.Repository.ClienteRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ClienteDto {

	@NotBlank(message = "Campo obrigatório")
	private String nome;
	
	@NotNull(message = "Campo obrigatorio")
	private TipoPessoa tipoPessoa;
	
	@CpfOrCnpj @NotBlank(message = "Campo obrigatório")
	private String cpfCnpj;
	
	@NotBlank(message = "Campo obrigatório") 
	private String telefone;
	
	@Email(message = "E-mail inválido")
	private String email;
	
	public ClienteDto() {}
	
	public ClienteDto(Cliente c) {
			this.nome = c.getNome();
			this.tipoPessoa = c.getTipoPessoa();
			this.cpfCnpj = c.getCpfCnpj();
			this.telefone = c.getTelefone();
			this.email = c.getEmail();
	}
	
	public Cliente converter(ClienteRepository cr) {
		return new Cliente(nome, tipoPessoa, cpfCnpj, telefone, email);
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

	public String getEmail() {
		return email;
	}

	public boolean exist(ClienteRepository cr) {
		List<Cliente> clientes = cr.findAll();
		return clientes.stream().anyMatch(c -> c.getCpfCnpj().equals(this.cpfCnpj));
	}
}
