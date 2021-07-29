package com.douglasdc.projetotecdev.services;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.repositories.OrdemDeServicoRepository;

@Service
public class DBService {

	@Autowired
	private OrdemDeServicoRepository ordemServicoRepository;
	
	public void instantiateTestDatabase() {
		
		OrdemDeServico os1 = new OrdemDeServico(null, LocalDate.now(), "Celular", "Marcos", StatusDaOrdemDeServico.PENDENTE);
		OrdemDeServico os2 = new OrdemDeServico(null, LocalDate.parse("2021-04-15"), "Televisão", "Joana", StatusDaOrdemDeServico.AGUARDANDO_CLIENTE);
		OrdemDeServico os3 = new OrdemDeServico(null, LocalDate.parse("2021-02-11"), "Microondas", "Pedro", StatusDaOrdemDeServico.APROVADA);
		OrdemDeServico os4 = new OrdemDeServico(null, LocalDate.parse("2021-06-28"),"Relógio", "Stefany", StatusDaOrdemDeServico.RECUSADA);
		
		ordemServicoRepository.saveAll(Arrays.asList(os1, os2, os3, os4));
	}
}
