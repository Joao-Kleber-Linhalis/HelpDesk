package com.jk.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jk.helpdesk.domain.dto.TecnicoDTO;
import com.jk.helpdesk.repositories.ChamadoRepository;
import com.jk.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	
	public TecnicoDTO findById(Integer id) {
		Optional<Tecnico> obj = repository.findByIdAndStatus(id,true);
		Optional<TecnicoDTO> tecnicoDTO = obj.map(TecnicoDTO::new);
		return tecnicoDTO.orElseThrow(() ->new ObjectNotFoundException("Objeto não encontrado! Id: " + id + " status: " + obj.get().getStatus()));
	}

	public List<TecnicoDTO> findAll() {
		List<Tecnico> tecnicoList = repository.findAll();
		return tecnicoList.stream().map(TecnicoDTO::new).toList();
	}

	public List<TecnicoDTO> findAllByStatus(boolean status){
		List<Tecnico> tecnicoList = repository.findByStatus(status);
		return tecnicoList.stream().map(TecnicoDTO::new).toList();
	}

	public TecnicoDTO create(TecnicoDTO dto) {
		pessoaService.validarCpfeEmail(dto.getCpf(),dto.getEmail(),dto.getId());
		Tecnico tecnico = new Tecnico(dto);
		return new TecnicoDTO(repository.save(tecnico));
	}

	public TecnicoDTO update(Integer id, TecnicoDTO dto) {
		dto.setId(id);
		TecnicoDTO tecnicoDTO = findById(id);
		/*pessoaService.validarCpfeEmail(tecnicoDTO.getCpf(), tecnicoDTO.getEmail(), tecnicoDTO.getId());
		Tecnico tecnico = new Tecnico(dto);
		return new TecnicoDTO(repository.save(tecnico));*/
		return create(dto);
	}

	public void delete(Integer id) {
		TecnicoDTO tecnicoDTO = findById(id);
		List<Integer> chamados = chamadoRepository.findChamadoIdsByPessoaId(tecnicoDTO.getId());
		if (!chamados.isEmpty()) {
			String chamadosIds = chamados.stream()
					.map(String::valueOf)
					.collect(Collectors.joining(", "));
			throw new DataIntegrityViolationException("Técnico possui " + chamados.size() + " chamados ainda em abertos. IDs dos chamados: " + chamadosIds);
		}
		tecnicoDTO.desativarPessoa();
		Tecnico tecnico = new Tecnico(tecnicoDTO);
		repository.save(tecnico);
	}
}
