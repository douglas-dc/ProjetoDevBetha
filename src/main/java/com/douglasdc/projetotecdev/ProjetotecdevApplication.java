package com.douglasdc.projetotecdev;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.repositories.OrdemDeServicoRepository;

@SpringBootApplication
public class ProjetotecdevApplication implements CommandLineRunner{

	@Autowired
	private OrdemDeServicoRepository ordemServicoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetotecdevApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		

		OrdemDeServico os1 = new OrdemDeServico(null, LocalDate.now(), "Celular", "Marcos", StatusDaOrdemDeServico.PENDENTE);
		OrdemDeServico os2 = new OrdemDeServico(null, LocalDate.parse("2021-04-15"), "Televisão", "Joana", StatusDaOrdemDeServico.AVALIADA);
		OrdemDeServico os3 = new OrdemDeServico(null, LocalDate.parse("2021-02-11"), "Microondas", "Pedro", StatusDaOrdemDeServico.APROVADA);
		OrdemDeServico os4 = new OrdemDeServico(null, LocalDate.parse("2021-06-28"),"Relógio", "Stefany", StatusDaOrdemDeServico.RECUSADA);
		
		ordemServicoRepository.saveAll(Arrays.asList(os1, os2, os3, os4));
	}
}
