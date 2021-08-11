package com.douglasdc.projetotecdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.Cliente;
import com.douglasdc.projetotecdev.repositories.ClienteRepository;
import com.douglasdc.projetotecdev.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		return obj;
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		if (obj.getNome() != null) {
			newObj.setNome(obj.getNome());
		}
		if (obj.getEmail() != null) {
			newObj.setEmail(obj.getEmail());
		}
		if (obj.getEndereco() != null) {
			newObj.setEndereco(obj.getEndereco());
		}
		if (obj.getTelefone() != null) {
			newObj.setTelefone(obj.getTelefone());
		}
		return repo.save(newObj);
	}
}
