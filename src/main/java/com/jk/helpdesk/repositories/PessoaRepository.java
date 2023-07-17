package com.jk.helpdesk.repositories;

import com.jk.helpdesk.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.helpdesk.domain.Pessoa;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    Optional<Pessoa> findByCpf(String cpf);

    Optional<Pessoa> findByEmail(String email);
}
