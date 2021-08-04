package com.douglasdc.projetotecdev.services;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.Equipamento;
import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.repositories.EquipamentoRepository;
import com.douglasdc.projetotecdev.repositories.OrdemDeServicoRepository;

@Service
public class DBService {

	@Autowired
	private OrdemDeServicoRepository ordemServicoRepository;
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	
	public void instantiateTestDatabase() {
		
		OrdemDeServico os1 = new OrdemDeServico(null, LocalDate.now(), "Marcos", StatusDaOrdemDeServico.PENDENTE);
		OrdemDeServico os2 = new OrdemDeServico(null, LocalDate.parse("2021-04-15"), "Joana", StatusDaOrdemDeServico.AGUARDANDO_CLIENTE);
		OrdemDeServico os3 = new OrdemDeServico(null, LocalDate.parse("2021-02-11"), "Pedro", StatusDaOrdemDeServico.APROVADA);
		OrdemDeServico os4 = new OrdemDeServico(null, LocalDate.parse("2021-06-28"), "Stefany", StatusDaOrdemDeServico.RECUSADA);
		
		Equipamento equip1 = new Equipamento(null, "Celular", "Tela quebrada", os1);
		os1.setEquipamento(equip1);
		
		Equipamento equip2 = new Equipamento(null, "Televisão", "Botão travado", os2);
		os2.setEquipamento(equip2);
		
		Equipamento equip3 = new Equipamento(null, "Notebook", "Teclado não funciona", os3);
		os3.setEquipamento(equip3);
		
		Equipamento equip4 = new Equipamento(null, "Smartwatch", "Problema com a bateria", os4);
		os4.setEquipamento(equip4);
		
		ordemServicoRepository.saveAll(Arrays.asList(os1, os2, os3, os4));
		equipamentoRepository.saveAll(Arrays.asList(equip1, equip2, equip3, equip4));
	}
}
