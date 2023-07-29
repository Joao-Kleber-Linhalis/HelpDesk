package com.jk.helpdesk.controllers;

import com.jk.helpdesk.domain.dto.ClienteDTO;
import com.jk.helpdesk.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok().body(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping("/")
	public ResponseEntity<List<ClienteDTO>> findAllByStatus(@RequestParam("status") String status) {
		if ("ativos".equals(status)) {
			return ResponseEntity.ok().body(service.findAllByStatus(true));
		} else if ("inativos".equals(status)) {
			return ResponseEntity.ok().body(service.findAllByStatus(false));
		} else {
			// Você pode tratar aqui o caso em que o valor do parâmetro é inválido
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO dto){
		dto.setId(null);
		ClienteDTO clienteDTO = service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(clienteDTO.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id,@Valid @RequestBody ClienteDTO dto){
		//ClienteDTO clienteDTO = service.update(id,dto);
		return ResponseEntity.ok().body(service.update(id,dto));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
