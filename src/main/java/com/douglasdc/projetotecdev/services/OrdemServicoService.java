package com.douglasdc.projetotecdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.OrdemServico;
import com.douglasdc.projetotecdev.repositories.OrdemServicoRepository;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository repo;
	
	public OrdemServico buscarPorId(Integer id) {
		Optional<OrdemServico> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
