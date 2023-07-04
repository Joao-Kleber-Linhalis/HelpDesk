package com.jk.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
}
