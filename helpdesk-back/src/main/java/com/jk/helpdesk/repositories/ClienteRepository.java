package com.jk.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.helpdesk.domain.Cliente;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("SELECT t FROM Cliente t WHERE t.status = :status")
    List<Cliente> findByStatus(boolean status);

    Optional<Cliente> findByIdAndStatus(Integer id, boolean status);
}
