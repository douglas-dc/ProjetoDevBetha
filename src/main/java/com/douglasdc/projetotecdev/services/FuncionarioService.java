package com.douglasdc.projetotecdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.Funcionario;
import com.douglasdc.projetotecdev.repositories.FuncionarioRepository;
import com.douglasdc.projetotecdev.services.exceptions.ObjectNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Funcionario find(Integer id) {
		Funcionario obj = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Funcionario não encontrado! Id: " + id + ", Tipo: " + Funcionario.class.getName()));
		return obj;
	}

	public Funcionario insert(Funcionario obj) {
		obj.setId(null);
		obj.setSenha(pe.encode(obj.getSenha()));
		return repo.save(obj);
	}

}
