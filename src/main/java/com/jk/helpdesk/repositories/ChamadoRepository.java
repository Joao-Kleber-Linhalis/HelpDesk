package com.jk.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.helpdesk.domain.Chamado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

    @Query("SELECT c.id FROM Chamado c WHERE (c.tecnico.id = :id OR c.cliente.id = :id) AND c.status <> 2")
    List<Integer> findChamadoIdsByPessoaId(@Param("id") Integer id);
}
