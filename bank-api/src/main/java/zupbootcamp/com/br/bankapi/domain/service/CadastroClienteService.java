package zupbootcamp.com.br.bankapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import zupbootcamp.com.br.bankapi.domain.exception.EntidadeEmUsoException;
import zupbootcamp.com.br.bankapi.domain.exception.EntidadeNaoEncontradaException;
import zupbootcamp.com.br.bankapi.domain.model.Cliente;
import zupbootcamp.com.br.bankapi.domain.repository.ClienteRepository;

public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public void destroy(Long id) {
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cliente de código %d não pode ser removida pois está em uso", id));
		} catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um Cliente com esse código %d", id));
		}
	}
}
