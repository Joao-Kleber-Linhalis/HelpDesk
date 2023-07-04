package com.jk.helpdesk;

import com.jk.helpdesk.domain.Chamado;
import com.jk.helpdesk.domain.Cliente;
import com.jk.helpdesk.domain.Tecnico;
import com.jk.helpdesk.domain.enums.Perfil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelpdeskApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

}
