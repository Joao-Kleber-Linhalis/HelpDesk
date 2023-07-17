package com.jk.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.helpdesk.domain.Tecnico;

import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
