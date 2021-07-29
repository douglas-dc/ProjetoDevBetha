package com.douglasdc.projetotecdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.repositories.OrdemDeServicoRepository;
import com.douglasdc.projetotecdev.services.exceptions.DataIntegrityException;
import com.douglasdc.projetotecdev.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemDeServicoService {

	@Autowired
	private OrdemDeServicoRepository repo;
	
	public OrdemDeServico find(Integer id) {
		Optional<OrdemDeServico> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Ordem de serviço não encontrada! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName()));
	}

	public OrdemDeServico insert(OrdemDeServico obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public List<OrdemDeServico> findAll() {
		return repo.findAll();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}

	public OrdemDeServico changeToAguardandoCliente(Integer id) {
		OrdemDeServico obj = find(id);
		if (!obj.equals(null)) {
			if (obj.getStatus().getCod() == 0) {
				obj.setStatus(StatusDaOrdemDeServico.AGUARDANDO_CLIENTE);
				return repo.save(obj);
			} else if (obj.getStatus().getCod() == 1) {
				return obj;
			} else {
				throw new DataIntegrityException("Violação de dados. Não é possível alterar o status para o valor desejado.");
			}
		}
		throw new ObjectNotFoundException("Ordem de serviço não encontrada! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName());
	}
	
	/*public List<OrdemDeServico> findByStatusAprovadas() {
		if (repo.findAprovadas(2).isEmpty()) {
			throw new ObjectNotFoundException("Nenhuma Ordem de serviço encontrada com o seguinte parâmetro: APROVADA");
		}
		return repo.findAprovadas(2);
	}*/
}
