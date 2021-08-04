package com.douglasdc.projetotecdev.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglasdc.projetotecdev.domain.Equipamento;
import com.douglasdc.projetotecdev.services.EquipamentoService;

@RestController
@RequestMapping(value="/equipamentos")
public class EquipamentoResource {

	@Autowired
	private EquipamentoService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Equipamento> find(@PathVariable Integer id) {
		Equipamento obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<List<Equipamento>> findAll() {
		List<Equipamento> lista = service.findAll();
		return ResponseEntity.ok().body(lista);
	}
}
