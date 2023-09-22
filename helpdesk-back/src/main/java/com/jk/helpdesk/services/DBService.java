package com.jk.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jk.helpdesk.domain.Chamado;
import com.jk.helpdesk.domain.Cliente;
import com.jk.helpdesk.domain.Tecnico;
import com.jk.helpdesk.domain.enums.Perfil;
import com.jk.helpdesk.domain.enums.Prioridade;
import com.jk.helpdesk.domain.enums.Status;
import com.jk.helpdesk.repositories.ChamadoRepository;
import com.jk.helpdesk.repositories.ClienteRepository;
import com.jk.helpdesk.repositories.TecnicoRepository;

@Service 
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void instaciaDB() {
		Tecnico tec1 = new Tecnico("Valdir Cesar", "14951880784","jklinhalis@gmail.com",encoder.encode("123"));
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente("Linus  Torvards", "14951880785", "torvads@mail.com", encoder.encode("123"));
		
		Chamado c1 = new Chamado(Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);

		Tecnico tec2 = new Tecnico("Fernanda Silva", "987654321", "fernanda.silva@example.com", encoder.encode("password"));
		tec2.addPerfil(Perfil.TECNICO);

		Cliente cli2 = new Cliente("John Doe", "123456789", "john.doe@example.com", encoder.encode("password"));

		Chamado c2 = new Chamado(Prioridade.ALTA, Status.ABERTO, "Chamado 02", "Segundo Chamado", tec2, cli2);

		Tecnico tec3 = new Tecnico("Ana Souza", "555555555", "ana.souza@example.com", encoder.encode("password"));
		tec3.addPerfil(Perfil.TECNICO);

		Cliente cli3 = new Cliente("Jane Smith", "999999999", "jane.smith@example.com", encoder.encode("password"));

		Chamado c3 = new Chamado(Prioridade.BAIXA, Status.ENCERRADO, "Chamado 03", "Terceiro Chamado", tec3, cli3);

		Tecnico tec4 = new Tecnico("Roberto Santos", "111111111", "roberto.santos@example.com", encoder.encode("password"));
		tec4.addPerfil(Perfil.TECNICO);

		Cliente cli4 = new Cliente("Maria Oliveira", "222222222", "maria.oliveira@example.com", encoder.encode("password"));

		Chamado c4 = new Chamado(Prioridade.ALTA, Status.ABERTO, "Chamado 04", "Quarto Chamado", tec3, cli3);

		tecnicoRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3, cli4));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4));

	}
}
