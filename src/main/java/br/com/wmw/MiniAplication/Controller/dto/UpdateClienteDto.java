package br.com.wmw.MiniAplication.Controller.dto;

import br.com.wmw.MiniAplication.Domain.Cliente;
import br.com.wmw.MiniAplication.Repository.ClienteRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateClienteDto {

	@NotBlank(message = "Campo obrigatório")
	private String telefone;

	@Email(message = "E-mail inválido")
	private String email;

	public UpdateClienteDto() {}

	public UpdateClienteDto(Cliente c) {
		this.telefone = c.getTelefone();
		this.email = c.getEmail();
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

	public Cliente update(int id, ClienteRepository clienteRepository) {
		Cliente c = clienteRepository.getReferenceById(id);
		c.setEmail(this.email);
		c.setTelefone(this.telefone);
		return c;
	}
	
}
