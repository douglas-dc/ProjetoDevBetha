package com.douglasdc.projetotecdev.services;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import com.amazonaws.services.licensemanager.model.AuthorizationException;
import com.douglasdc.projetotecdev.domain.Funcionario;
//import com.douglasdc.projetotecdev.domain.enums.Perfil;
import com.douglasdc.projetotecdev.repositories.FuncionarioRepository;
//import com.douglasdc.projetotecdev.security.UserSS;
import com.douglasdc.projetotecdev.services.exceptions.ObjectNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repo;

//	@Autowired
//	private BCryptPasswordEncoder pe;

	public Funcionario find(Integer id) {
		Funcionario obj = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Funcionario não encontrado! Id: " + id + ", Tipo: " + Funcionario.class.getName()));
		return obj;
	}

	public Funcionario insert(Funcionario obj) {
		obj.setId(null);
		obj.setSenha((obj.getSenha()));
		return repo.save(obj);
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
//	public Funcionario findByEmail(String email) {
//
//		UserSS user = UserService.authenticated();
//		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
//			throw new AuthorizationException("Acesso negado");
//		}
//
//		Funcionario obj = repo.findByEmail(email);
//		if (obj == null) {
//			throw new ObjectNotFoundException(
//					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Funcionario.class.getName());
//		}
//		return obj;
//	}

}
