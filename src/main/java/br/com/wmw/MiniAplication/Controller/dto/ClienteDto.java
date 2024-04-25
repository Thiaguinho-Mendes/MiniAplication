package br.com.wmw.MiniAplication.Controller.dto;


import java.util.List;

import br.com.wmw.MiniAplication.Controller.Validate.CpfOrCnpj;
import br.com.wmw.MiniAplication.Domain.Cliente;
import br.com.wmw.MiniAplication.Domain.Origem;
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
	
	private Origem origem;
	
	public ClienteDto() {}
	
	public ClienteDto(Cliente c) {
			this.nome = c.getNome();
			this.tipoPessoa = c.getTipoPessoa();
			this.cpfCnpj = c.getCpfCnpj();
			this.telefone = c.getTelefone();
			this.email = c.getEmail();
			this.origem = c.getOrigem();
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
	
	public Origem getOrigem() {
		return origem;
	}

	public Cliente converter(ClienteRepository cr) {
		if(getOrigem() == null) {
			origem = Origem.WEB;
		}
		return new Cliente(nome, tipoPessoa, cpfCnpj, telefone, email, origem);
	}
	
	public UpdateClienteDto converterUpdateClienteDto(ClienteRepository cr) {
		Cliente c = converter(cr);
		return new UpdateClienteDto(c);
	}
	
	public boolean exist(ClienteRepository cr) {
		List<Cliente> clientes = cr.findAll();
		return clientes.stream().anyMatch(c -> c.getCpfCnpj().equals(this.cpfCnpj));
	}
}
