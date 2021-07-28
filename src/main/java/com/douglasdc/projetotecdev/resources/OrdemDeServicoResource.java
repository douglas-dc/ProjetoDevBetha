package com.douglasdc.projetotecdev.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.dto.OrdemDeServicoDTO;
import com.douglasdc.projetotecdev.services.OrdemDeServicoService;

@RestController
@RequestMapping(value="/ordens")
public class OrdemDeServicoResource {

	@Autowired
	private OrdemDeServicoService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<OrdemDeServico> find(@PathVariable Integer id) {
		OrdemDeServico obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody OrdemDeServico obj) {
		obj.setInstante(LocalDate.now());
		obj.setStatus(StatusDaOrdemDeServico.PENDENTE);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public ResponseEntity<List<OrdemDeServicoDTO>> findAll() {
		List<OrdemDeServico> lista = service.findAll();
		List<OrdemDeServicoDTO> listaDto = lista.stream().map(obj -> new OrdemDeServicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDto);
	}

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value="/{id}/avaliada")
	public ResponseEntity<OrdemDeServico> avaliaOrdem(@PathVariable Integer id) {
		OrdemDeServico obj = service.avaliarOrdem(id);
		return ResponseEntity.ok().body(obj);
	}
	
	/*@GetMapping(value="/aprovadas")
	public ResponseEntity<List<OrdemDeServico>> findByStatus() {
		List<OrdemDeServico> obj = service.findByStatusAprovadas();
		return ResponseEntity.ok().body(obj);
	}*/
	
	/*@PutMapping(value="/{id}")
	public ResponseEntity<Void> updateStatus(@RequestBody OrdemDeServicoDTO2 objDto, @PathVariable Integer id) {
		OrdemDeServico obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}*/
}