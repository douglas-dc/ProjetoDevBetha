package com.douglasdc.projetotecdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.repositories.OrdemDeServicoRepository;
import com.douglasdc.projetotecdev.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemDeServicoService {

	@Autowired
	private OrdemDeServicoRepository repo;
	
	public OrdemDeServico buscarPorId(Integer id) {
		Optional<OrdemDeServico> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Ordem de serviço não encontrada! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName()));
	}

	public OrdemDeServico inserirOrdem(OrdemDeServico obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public List<OrdemDeServico> buscarTodos() {
		return repo.findAll();
	}
	
	public void deletarPorId(Integer id) {
		repo.deleteById(id);
	}

	public List<OrdemDeServico> buscarPorStatus(){
		if (repo.findAprovadas(2).isEmpty()) {
			throw new ObjectNotFoundException("Nenhuma Ordem de serviço encontrada com o seguinte parâmetro: APROVADA");
		}
		return repo.findAprovadas(2);
	}
}
