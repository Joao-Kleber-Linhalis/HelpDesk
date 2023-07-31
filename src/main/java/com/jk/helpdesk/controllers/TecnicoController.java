package com.jk.helpdesk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import com.jk.helpdesk.domain.dto.TecnicoDTO;
import com.jk.helpdesk.services.TecnicoService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoController {

	@Autowired
	private TecnicoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok().body(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll(){
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping("/byStatus")
	public ResponseEntity<List<TecnicoDTO>> findAllByStatus(@RequestParam("status") String status) {
		if ("ativos".equals(status)) {
			return ResponseEntity.ok().body(service.findAllByStatus(true));
		} else if ("inativos".equals(status)) {
			return ResponseEntity.ok().body(service.findAllByStatus(false));
		} else {
			// Você pode tratar aqui o caso em que o valor do parâmetro é inválido
			return ResponseEntity.badRequest().build();
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO dto){
		dto.setId(null);
		TecnicoDTO tecnicoDTO = service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(tecnicoDTO.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id,@Valid @RequestBody TecnicoDTO dto){
		//TecnicoDTO tecnicoDTO = service.update(id,dto);
		return ResponseEntity.ok().body(service.update(id,dto));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
