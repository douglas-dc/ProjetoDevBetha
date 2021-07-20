package com.douglasdc.projetotecdev.resources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douglasdc.projetotecdev.domain.OrdemServico;

@RestController
@RequestMapping(value="/ordens")
public class OrdemServicoResource {

	@RequestMapping(method=RequestMethod.GET)
	public List<OrdemServico> listarTodos() {
		
		OrdemServico o1 = new OrdemServico(1, LocalDate.now());
		OrdemServico o2 = new OrdemServico(2, LocalDate.ofYearDay(5,10));
		
		List<OrdemServico> lista = new ArrayList<>();
		lista.addAll(Arrays.asList(o1, o2));
		return lista;
	}
	
}