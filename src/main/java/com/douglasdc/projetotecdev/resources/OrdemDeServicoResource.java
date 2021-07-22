package com.douglasdc.projetotecdev.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.services.OrdemDeServicoService;

@RestController
@RequestMapping(value="/ordens")
public class OrdemDeServicoResource {

	@Autowired
	private OrdemDeServicoService service;

	@GetMapping(value="/{id}")
	public ResponseEntity<OrdemDeServico> find(@PathVariable Integer id) {
		OrdemDeServico obj = service.buscarPorId(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody OrdemDeServico obj) {
		obj = service.inserirOrdem(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}