package com.douglasdc.projetotecdev.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.dto.OrdemDeServicoDTO;
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

	public OrdemDeServico updateStatusToAprovada(Integer id) {
		OrdemDeServico obj = find(id);
		obj.setStatus(StatusDaOrdemDeServico.APROVADA);
		return repo.save(obj);
	}

	public OrdemDeServico fromDTO(@Valid OrdemDeServicoDTO objDto) {
		return new OrdemDeServico(objDto.getId(), objDto.getInstante(), objDto.getClienteNome(),
				objDto.getEquipamentoTipo(), objDto.getStatus());
	}

	public OrdemDeServico update(OrdemDeServico obj) {
		OrdemDeServico newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(OrdemDeServico newObj, OrdemDeServico obj) {
		newObj.setInstante(obj.getInstante());
		newObj.setCliente(obj.getCliente());
		newObj.setEquipamento(obj.getEquipamento());
		newObj.setStatus(obj.getStatus());
	}

	public OrdemDeServico updateStatus(@Valid StatusDaOrdemDeServico status, Integer id) {
		OrdemDeServico obj = find(id);
		validarStatus(status, obj);
		obj.setStatus(status);
		return repo.save(obj);
	}
	
	public OrdemDeServico validarStatus(StatusDaOrdemDeServico status, OrdemDeServico obj) {
		if (obj.getStatus().getCod() == 0) {
			if (status == StatusDaOrdemDeServico.AGUARDANDO_CLIENTE || status == StatusDaOrdemDeServico.PENDENTE) {
				obj.setStatus(status);
				return obj;
			} else {
				throw new DataIntegrityException("Violação de dados. Não é possível alterar o status para o valor desejado.");
			}
		}	
			
		if (obj.getStatus().getCod() == 1) {
			if (status == StatusDaOrdemDeServico.APROVADA || status == StatusDaOrdemDeServico.RECUSADA || 
					status == StatusDaOrdemDeServico.AGUARDANDO_CLIENTE) {
				obj.setStatus(status);
				return obj;
			} else {
				throw new DataIntegrityException("Violação de dados. Não é possível alterar o status para o valor desejado.");
			}
		}
		
		if (obj.getStatus().getCod() == 2 || obj.getStatus().getCod() == 3) {
			
		}
		
		return null;
	}

	/*public List<OrdemDeServico> findByStatusAprovadas() {
		if (repo.findAprovadas(2).isEmpty()) {
			throw new ObjectNotFoundException("Nenhuma Ordem de serviço encontrada com o seguinte parâmetro: APROVADA");
		}
		return repo.findAprovadas(2);
	}*/
	
	/*public OrdemDeServico changeStatusToAguardandoCliente(Integer id) {
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
	}*/
}
