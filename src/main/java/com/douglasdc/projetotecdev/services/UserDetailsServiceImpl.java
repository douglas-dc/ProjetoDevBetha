package com.douglasdc.projetotecdev.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.Funcionario;
import com.douglasdc.projetotecdev.repositories.FuncionarioRepository;
import com.douglasdc.projetotecdev.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private FuncionarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Funcionario func = repo.findByEmail(email);
		if (func == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSS(func.getId(), func.getEmail(), func.getSenha(), Arrays.asList(func.getPerfil()));
	}

}
