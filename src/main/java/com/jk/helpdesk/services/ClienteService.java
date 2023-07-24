package com.jk.helpdesk.services;

import com.jk.helpdesk.domain.Cliente;
import com.jk.helpdesk.domain.dto.ClienteDTO;
import com.jk.helpdesk.repositories.ChamadoRepository;
import com.jk.helpdesk.repositories.ClienteRepository;
import com.jk.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private PessoaService pessoaService;
	
	public ClienteDTO findById(Integer id) {
		Optional<Cliente> obj = repository.findByIdAndStatus(id,true);
		Optional<ClienteDTO> clienteDTO = obj.map(ClienteDTO::new);
		return clienteDTO.orElseThrow(() ->new ObjectNotFoundException(findByIdDisable(id) + " Id: " + id));
	}

	public String findByIdDisable(Integer id) {
		Optional<Cliente> obj = repository.findByIdAndStatus(id,false);
		Optional<ClienteDTO> clienteDTO = obj.map(ClienteDTO::new);
		if (clienteDTO.isPresent()){
			return "Objeto atualmente desativado";
		}
		else {
			return "Objeto não encontrado";
		}
	}

	public List<ClienteDTO> findAll() {
		List<Cliente> clienteList = repository.findAll();
		return clienteList.stream().map(ClienteDTO::new).toList();
	}

	public List<ClienteDTO> findAllByStatus(boolean status){
		List<Cliente> clienteList = repository.findByStatus(status);
		return clienteList.stream().map(ClienteDTO::new).toList();
	}

	public ClienteDTO create(ClienteDTO dto) {
		pessoaService.validarCpfeEmail(dto.getCpf(),dto.getEmail(),dto.getId());
		Cliente cliente = new Cliente(dto);
		return new ClienteDTO(repository.save(cliente));
	}

	public ClienteDTO update(Integer id, ClienteDTO dto) {
		dto.setId(id);
		ClienteDTO clienteDTO = findById(id);
		/*pessoaService.validarCpfeEmail(clienteDTO.getCpf(), clienteDTO.getEmail(), clienteDTO.getId());
		Cliente cliente = new Cliente(dto);
		return new ClienteDTO(repository.save(cliente));*/
		return create(dto);
	}

	public void delete(Integer id) {
		ClienteDTO clienteDTO = findById(id);
		List<Integer> chamados = chamadoRepository.findChamadoIdsByPessoaId(clienteDTO.getId());
		if (!chamados.isEmpty()) {
			String chamadosIds = chamados.stream()
					.map(String::valueOf)
					.collect(Collectors.joining(", "));
			throw new DataIntegrityViolationException("Cliente possui " + chamados.size() + " chamados ainda em abertos. IDs dos chamados: " + chamadosIds);
		}
		clienteDTO.desativarPessoa();
		Cliente cliente = new Cliente(clienteDTO);
		repository.save(cliente);
	}
}
