package com.jk.helpdesk.services;

import java.util.Optional;

import com.jk.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.helpdesk.domain.Tecnico;
import com.jk.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() ->new ObjectNotFoundException("Objeto não encontrado! Id: " + id ));
	}
}
