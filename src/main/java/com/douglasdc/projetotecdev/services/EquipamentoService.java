package com.douglasdc.projetotecdev.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglasdc.projetotecdev.domain.Equipamento;
import com.douglasdc.projetotecdev.repositories.EquipamentoRepository;
import com.douglasdc.projetotecdev.services.exceptions.ObjectNotFoundException;

@Service
public class EquipamentoService {

	@Autowired
	private EquipamentoRepository repo;
	
	public Equipamento find(Integer id) {
		Equipamento obj = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Equipamento não encontrado! Id: " + id + ", Tipo: " + Equipamento.class.getName()));
		return obj;
	}
	
	public List<Equipamento> findAll() {
		return repo.findAll();
	}
}
