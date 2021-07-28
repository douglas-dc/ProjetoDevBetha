package com.douglasdc.projetotecdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.dto.OrdemDeServicoDTO2;
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
	
	public OrdemDeServico avaliarOrdem(Integer id) {
		Optional<OrdemDeServico> obj = repo.findById(id);
		if (!obj.isEmpty()) {
			if (obj.get().getStatus().getCod() == 0) {
				obj.get().setStatus(StatusDaOrdemDeServico.AVALIADA);
				repo.save(obj.get());
				return obj.get();
			} else if (obj.get().getStatus().getCod() != 1) {
				throw new DataIntegrityException("Violação de dados. Não é possível alterar o status para o valor desejado.");
			}
		}
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Ordem de serviço não encontrada! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName()));
	}

	public List<OrdemDeServico> findByStatusAprovadas(){
		if (repo.findAprovadas(2).isEmpty()) {
			throw new ObjectNotFoundException("Nenhuma Ordem de serviço encontrada com o seguinte parâmetro: APROVADA");
		}
		return repo.findAprovadas(2);
	}
	
	public OrdemDeServico update(OrdemDeServico obj) {
		OrdemDeServico newObj = find(obj.getId());
		updateData(newObj, obj); 
		if (newObj.getStatus().getCod() == 1) {
			System.out.println(newObj);
		}
		if (newObj.getStatus().getCod() == 2 || newObj.getStatus().getCod() == 3) {
			System.out.println(newObj);
		}
		return repo.save(newObj);
	}
	
	private void updateData(OrdemDeServico newObj, OrdemDeServico obj) {
		newObj.setStatus(obj.getStatus());
	}

	public OrdemDeServico fromDTO(OrdemDeServicoDTO2 objDto) {
		return new OrdemDeServico(null, null, null, null, objDto.getStatus());
	}
}
