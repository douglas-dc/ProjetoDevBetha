package com.douglasdc.projetotecdev.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.dto.OrdemDeServicoDTO;
import com.douglasdc.projetotecdev.dto.OrdemDeServicoDTOPut;
import com.douglasdc.projetotecdev.services.OrdemDeServicoService;

@RestController
@RequestMapping(value="/ordens")
public class OrdemDeServicoResource {

	@Autowired
	private OrdemDeServicoService service;
	
	@Value("${img.prefix.url}")
	private String prefixUrl;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<OrdemDeServico> find(@PathVariable Integer id) {
		OrdemDeServico obj = service.find(id);
		obj.setImageName(prefixUrl + obj.getImageName());
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody OrdemDeServico obj) {
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
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody OrdemDeServicoDTOPut objDto, @PathVariable Integer id) {
		service.update(objDto, id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{id}/situacao")
	public ResponseEntity<Void> update(@RequestParam(value="status") StatusDaOrdemDeServico status, @PathVariable Integer id) {
		service.updateStatus(status, id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value="/{id}/aprovada")
	public ResponseEntity<OrdemDeServico> setStatusAprovada(@PathVariable Integer id) {
		service.updateStatusToAprovada(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value="/{id}/recusada")
	public ResponseEntity<OrdemDeServico> setStatusRecusada(@PathVariable Integer id) {
		service.updateStatusToRecusada(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value="/{id}/avarias")
	public ResponseEntity<Void> insert(@RequestParam(name="file") MultipartFile file, @PathVariable Integer id) {
		URI uri = service.uploadAvariaImage(file, id);
		return ResponseEntity.created(uri).build();
	}
}