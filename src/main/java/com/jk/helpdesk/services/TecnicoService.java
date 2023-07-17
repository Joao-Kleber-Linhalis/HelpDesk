package com.jk.helpdesk.services;

import java.util.List;
import java.util.Optional;

import com.jk.helpdesk.domain.dto.TecnicoDTO;
import com.jk.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.helpdesk.domain.Tecnico;
import com.jk.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;

	@Autowired
	private PessoaService pessoaService;
	
	public TecnicoDTO findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		Optional<TecnicoDTO> tecnicoDTO = obj.map(TecnicoDTO::new);
		return tecnicoDTO.orElseThrow(() ->new ObjectNotFoundException("Objeto não encontrado! Id: " + id ));
	}

	public List<TecnicoDTO> findAll() {
		List<Tecnico> tecnicoList = repository.findAll();
		return tecnicoList.stream().map(TecnicoDTO::new).toList();
	}

	public TecnicoDTO create(TecnicoDTO dto) {
		dto.setId(null);
		pessoaService.validarCpfeEmail(dto.getCpf(),dto.getEmail());
		Tecnico tecnico = new Tecnico(dto);
		return new TecnicoDTO(repository.save(tecnico));
	}

}
