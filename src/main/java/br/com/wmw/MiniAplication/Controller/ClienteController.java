package br.com.wmw.MiniAplication.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.wmw.MiniAplication.Controller.Validate.HandlerValidator;
import br.com.wmw.MiniAplication.Controller.dto.ClienteDto;
import br.com.wmw.MiniAplication.Controller.dto.UpdateClienteDto;
import br.com.wmw.MiniAplication.Domain.Cliente;
import br.com.wmw.MiniAplication.Repository.ClienteRepository;
import jakarta.validation.Valid;

@RestController
@ResponseBody
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Cliente> findByCodigo(@PathVariable("id") int id) {
		return clienteRepository.findById(id);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid ClienteDto dto, UriComponentsBuilder uriBuilder) {
		Cliente c = dto.converter(clienteRepository);
		if (dto.exist(clienteRepository)) {
			return ResponseEntity.internalServerError().body(new HandlerValidator().HandlerInternalServer());
		}
		clienteRepository.save(c);
		URI uri = uriBuilder.path("/cliente/{codigo}").buildAndExpand(c.getCodigo()).toUri();
		return ResponseEntity.created(uri).body(new ClienteDto(c));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteDto> update(@PathVariable("id") int id, @RequestBody @Valid UpdateClienteDto dto) {
		Cliente c = dto.update(id, clienteRepository);
		return ResponseEntity.ok(new ClienteDto(c));
	}

	@DeleteMapping("/{id}")
	public void deleteByCodigo(@PathVariable("id") int codigo) {
		clienteRepository.deleteById(codigo);
	}

}
