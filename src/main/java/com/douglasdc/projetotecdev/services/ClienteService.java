package com.douglasdc.projetotecdev.services;

import java.util.List;

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
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Cliente update(Cliente obj) {
		find(obj.getId());
		return repo.save(obj);
	}
}
