package com.douglasdc.projetotecdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.repositories.OrdemServicoRepository;

@Service
public class OrdemDeServicoService {

	@Autowired
	private OrdemServicoRepository repo;
	
	public OrdemDeServico buscarPorId(Integer id) {
		Optional<OrdemDeServico> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
