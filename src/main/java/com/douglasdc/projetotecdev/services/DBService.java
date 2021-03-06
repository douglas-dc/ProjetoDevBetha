package com.douglasdc.projetotecdev.services;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.Cliente;
import com.douglasdc.projetotecdev.domain.Equipamento;
import com.douglasdc.projetotecdev.domain.Funcionario;
import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.Perfil;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.repositories.ClienteRepository;
import com.douglasdc.projetotecdev.repositories.EquipamentoRepository;
import com.douglasdc.projetotecdev.repositories.FuncionarioRepository;
import com.douglasdc.projetotecdev.repositories.OrdemDeServicoRepository;

@Service
public class DBService {

	@Autowired
	private OrdemDeServicoRepository ordemServicoRepository;
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void instantiateTestDatabase() {
		
		Funcionario func1 = new Funcionario(null, "Lionel", "lionel@gmail.com", pe.encode("123"), Perfil.ADMIN);
		Funcionario func2 = new Funcionario(null, "Cristiano", "cristiano@gmail.com", pe.encode("123"), Perfil.TECNICO);
		Funcionario func3 = new Funcionario(null, "Neymar", "neymar@gmail.com", pe.encode("123"), Perfil.RECEPCIONISTA);
		funcionarioRepository.saveAll(Arrays.asList(func1, func2, func3));
		
		Cliente cli1 = new Cliente(null, "Pablo", "Rua Conceição, número 30", "pablo@gmail.com", "9999-8888");
		Cliente cli2 = new Cliente(null, "Joana", "Rua Graça, número 95", "joana@gmail.com", "9999-7777");
		Cliente cli3 = new Cliente(null, "Fabrícia", "Avenida Pinheiros, número 101", "fabricia@gmail.com", "9999-6666");
		Cliente cli4 = new Cliente(null, "Jackinho", "Rua Acácia, número 52", "jackinho@gmail.com", "9999-5555");
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3, cli4));
		
		OrdemDeServico os1 = new OrdemDeServico(null, LocalDate.now(), cli1, StatusDaOrdemDeServico.PENDENTE);
		OrdemDeServico os2 = new OrdemDeServico(null, LocalDate.parse("2021-04-15"), cli2, StatusDaOrdemDeServico.AGUARDANDO_CLIENTE);
		OrdemDeServico os3 = new OrdemDeServico(null, LocalDate.parse("2021-02-11"), cli3, StatusDaOrdemDeServico.APROVADA);
		OrdemDeServico os4 = new OrdemDeServico(null, LocalDate.parse("2021-06-28"), cli4, StatusDaOrdemDeServico.RECUSADA);
		
		Equipamento equip1 = new Equipamento(null, "Celular", "Tela quebrada", os1);
		Equipamento equip2 = new Equipamento(null, "Televisão", "Botão travado", os2);
		Equipamento equip3 = new Equipamento(null, "Notebook", "Teclado não funciona", os3);
		Equipamento equip4 = new Equipamento(null, "Smartwatch", "Problema com a bateria", os4);
		Equipamento equip5 = new Equipamento(null, "Alexa", "Sem som", os1);
		Equipamento equip6 = new Equipamento(null, "Tablet", "Não carrega até 100%", os1);
		Equipamento equip7 = new Equipamento(null, "Headset", "Microfone não liga", os2);
		
		os1.getEquipamentos().addAll(Arrays.asList(equip1, equip5, equip6));
		os2.getEquipamentos().addAll(Arrays.asList(equip2, equip7));
		
		ordemServicoRepository.saveAll(Arrays.asList(os1, os2, os3, os4));
		equipamentoRepository.saveAll(Arrays.asList(equip1, equip2, equip3, equip4, equip5, equip6, equip7));
	}
}
