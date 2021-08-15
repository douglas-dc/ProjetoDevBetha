package com.douglasdc.projetotecdev.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

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

import com.douglasdc.projetotecdev.domain.Equipamento;
import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
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
		for (Equipamento equip : obj.getEquipamentos()) {
    		equip.setImageName(prefixUrl + equip.getImageName());
    	}
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
    public ResponseEntity<List<OrdemDeServico>> findAll() {
        List<OrdemDeServico> list = service.findAll();
        for (OrdemDeServico obj : list) {
        	for (Equipamento equip : obj.getEquipamentos()) {
        		equip.setImageName(prefixUrl + equip.getImageName());
        	}
        }
        return ResponseEntity.ok().body(list);
    }

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody OrdemDeServico obj, @PathVariable Integer id) {
		service.update(obj, id);
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
	
	@PostMapping(value="/avarias/equipamento/{id}")
	public ResponseEntity<Void> insert(@RequestParam(name="file") MultipartFile file, @PathVariable Integer id) {
		URI uri = service.uploadAvariaImage(file, id);
		return ResponseEntity.created(uri).build();
	}
}