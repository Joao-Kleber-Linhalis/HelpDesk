package com.jk.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
	
	
}
