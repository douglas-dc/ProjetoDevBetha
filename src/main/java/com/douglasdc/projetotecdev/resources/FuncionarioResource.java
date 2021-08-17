package com.douglasdc.projetotecdev.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douglasdc.projetotecdev.domain.Funcionario;
import com.douglasdc.projetotecdev.services.FuncionarioService;

@RestController
@RequestMapping(value="/funcionarios")
public class FuncionarioResource {

	@Autowired
	private FuncionarioService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Funcionario> find(@PathVariable Integer id) {
		Funcionario obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	//@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Funcionario obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
		@GetMapping
	    public ResponseEntity<List<Funcionario>> findAll() {
	        List<Funcionario> list = service.findAll();
	        return ResponseEntity.ok().body(list);
	    }
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody Funcionario obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value="/email")
	public ResponseEntity<Funcionario> find(@RequestParam(value="value") String email) {
		Funcionario obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
}
