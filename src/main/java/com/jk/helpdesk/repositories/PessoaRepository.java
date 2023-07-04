package com.jk.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
	
}
