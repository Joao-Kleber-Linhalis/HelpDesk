package com.jk.helpdesk.services;

import com.jk.helpdesk.domain.Pessoa;
import com.jk.helpdesk.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public void validarCpfeEmail(String cpf,String email, Integer id){
        Optional<Pessoa> optionalPessoaCpf = repository.findByCpf(cpf);
        if(optionalPessoaCpf.isPresent() && !Objects.equals(optionalPessoaCpf.get().getId(), id)){
            throw new IllegalArgumentException("Cpf já cadastrado");
        }
        Optional<Pessoa> optionalPessoaEmail = repository.findByEmail(email);
        if(optionalPessoaEmail.isPresent()&& !Objects.equals(optionalPessoaEmail.get().getId(), id)){
            throw new IllegalArgumentException("Email já cadastrado");
        }
    }
}
