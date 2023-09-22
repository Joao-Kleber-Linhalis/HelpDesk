package com.jk.helpdesk.services;

import com.jk.helpdesk.domain.Chamado;
import com.jk.helpdesk.domain.Cliente;
import com.jk.helpdesk.domain.dto.ClienteDTO;
import com.jk.helpdesk.repositories.ChamadoRepository;
import com.jk.helpdesk.repositories.ClienteRepository;
import com.jk.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.jk.helpdesk.services.exceptions.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public ClienteDTO findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		Optional<ClienteDTO> clienteDTO = obj.map(ClienteDTO::new);
		return clienteDTO.orElseThrow(() ->new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}



	public List<ClienteDTO> findAll() {
		List<Cliente> clienteList = repository.findAll();
		return clienteList.stream().map(ClienteDTO::new).collect(Collectors.toList());
	}

	public ClienteDTO create(ClienteDTO dto) {
		pessoaService.validarCpfeEmail(dto.getCpf(),dto.getEmail(),dto.getId());
		dto.setId(null);
		dto.setSenha(encoder.encode(dto.getSenha()));
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
		findById(id);
		Optional<Cliente> obj = repository.findById(id);
		if (obj.get().getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		}

		repository.deleteById(id);
	}
}
