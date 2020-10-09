package zupbootcamp.com.br.bankapi.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import zupbootcamp.com.br.bankapi.domain.exception.EntidadeEmUsoException;
import zupbootcamp.com.br.bankapi.domain.exception.EntidadeNaoEncontradaException;
import zupbootcamp.com.br.bankapi.domain.model.Cliente;
import zupbootcamp.com.br.bankapi.domain.repository.ClienteRepository;
import zupbootcamp.com.br.bankapi.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	public ClienteRepository clienteRepository;

	@Autowired
	public CadastroClienteService cadastroCliente;

	@GetMapping
	public List<Cliente> list() {
		return clienteRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> show(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente) {
		return cadastroCliente.save(cliente);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cliente cliente) {
		
		try {
			Optional<Cliente> clienteAtual = clienteRepository.findById(id);
			
			if (clienteAtual.isPresent()) {
				BeanUtils.copyProperties(cliente, clienteAtual.get(), "id", "dataNascimento");
				Cliente clienteAtualizado = cadastroCliente.save(clienteAtual.get());

				return ResponseEntity.ok(clienteAtualizado);
			}
			return ResponseEntity.notFound().build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> destroy(@PathVariable Long id) {
		try {
			cadastroCliente.destroy(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

	}
}
