package com.jk.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.helpdesk.domain.Tecnico;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    @Query("SELECT t FROM Tecnico t WHERE t.status = :status")
    List<Tecnico> findByStatus(boolean status);

    Optional<Tecnico> findByIdAndStatus(Integer id, boolean status);
}
