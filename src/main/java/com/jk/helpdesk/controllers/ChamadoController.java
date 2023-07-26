package com.jk.helpdesk.controllers;

import com.jk.helpdesk.domain.Chamado;
import com.jk.helpdesk.domain.dto.ChamadoDTO;
import com.jk.helpdesk.services.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(chamadoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll(){
        return ResponseEntity.ok().body(chamadoService.findAll());
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO chamadoDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(chamadoService.create(chamadoDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> uptade(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO chamadoDTO){
        return ResponseEntity.ok().body(chamadoService.update(id,chamadoDTO));
    }
}
