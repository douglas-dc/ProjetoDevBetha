package com.douglasdc.projetotecdev.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douglasdc.projetotecdev.domain.Cliente;
import com.douglasdc.projetotecdev.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	//@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	@GetMapping(value="/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	//@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Cliente obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	@GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody Cliente obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
}
