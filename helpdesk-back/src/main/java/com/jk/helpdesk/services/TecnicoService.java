package com.jk.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jk.helpdesk.domain.dto.TecnicoDTO;
import com.jk.helpdesk.repositories.ChamadoRepository;
import com.jk.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.jk.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jk.helpdesk.domain.Tecnico;
import com.jk.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public TecnicoDTO findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		Optional<TecnicoDTO> tecnicoDTO = obj.map(TecnicoDTO::new);
		return tecnicoDTO.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));	}


	public List<TecnicoDTO> findAll() {
		List<Tecnico> tecnicoList = repository.findAll();
		return tecnicoList.stream().map(TecnicoDTO::new).collect(Collectors.toList());
	}



	public TecnicoDTO create(TecnicoDTO dto) {
		pessoaService.validarCpfeEmail(dto.getCpf(),dto.getEmail(),dto.getId());
		dto.setId(null);
		dto.setSenha(encoder.encode(dto.getSenha()));
		Tecnico tecnico = new Tecnico(dto);
		return new TecnicoDTO(repository.save(tecnico));
	}

	public TecnicoDTO update(Integer id, TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = new Tecnico(findById(id));

		if(!objDTO.getSenha().equals(oldObj.getSenha()))
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));

		pessoaService.validarCpfeEmail(objDTO.getCpf(), objDTO.getEmail(), objDTO.getId());
		oldObj = new Tecnico(objDTO);
		return new TecnicoDTO(repository.save(oldObj));
	}

	public void delete(Integer id) {
		findById(id);
		Optional<Tecnico> obj = repository.findById(id);
		if (obj.get().getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
		}

		repository.deleteById(id);
	}
}
