package com.douglasdc.projetotecdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.repositories.OrdemDeServicoRepository;

@Service
public class OrdemDeServicoService {

	@Autowired
	private OrdemDeServicoRepository repo;
	
	public OrdemDeServico buscarPorId(Integer id) {
		Optional<OrdemDeServico> obj = repo.findById(id);
		return obj.orElse(null);
	}

	public OrdemDeServico inserirOrdem(OrdemDeServico obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public List<OrdemDeServico> buscarTodos() {
		return repo.findAll();
	}
}
