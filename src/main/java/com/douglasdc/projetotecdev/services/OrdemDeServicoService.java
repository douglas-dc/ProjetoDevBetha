package com.douglasdc.projetotecdev.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
import com.douglasdc.projetotecdev.dto.OrdemDeServicoDTOPut;
import com.douglasdc.projetotecdev.repositories.EquipamentoRepository;
import com.douglasdc.projetotecdev.repositories.OrdemDeServicoRepository;
import com.douglasdc.projetotecdev.services.exceptions.DataIntegrityException;
import com.douglasdc.projetotecdev.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemDeServicoService {

	@Autowired
	private OrdemDeServicoRepository repo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Value("${img.prefix.name}")
	private String prefixName;
	
	@Value("${img.prefix.url}")
	private String prefixUrl;
	
	public OrdemDeServico find(Integer id) {
		OrdemDeServico obj = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Ordem de serviço não encontrada! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName()));
		return obj;
	}

	public OrdemDeServico insert(OrdemDeServico obj) {
		obj.setId(null);
		obj.getEquipamento().setOrdemDeServico(obj);
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj = repo.save(obj);
		equipamentoRepository.save(obj.getEquipamento());
		return obj;
	}

	public List<OrdemDeServico> findAll() {
		return repo.findAll();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}

	public OrdemDeServico update(OrdemDeServicoDTOPut obj) {
		OrdemDeServico newObj = find(obj.getId());
		if (obj.getInstante() != null) {
			newObj.setInstante(obj.getInstante());
		}
		if (obj.getClienteId() != null) {
			newObj.setCliente(clienteService.find(obj.getClienteId()));
		}
		if (obj.getEquipamentoDescricao() != null) {
			newObj.getEquipamento().setDescricao(obj.getEquipamentoDescricao());
		}
		if (obj.getEquipamentoTipo() != null) {
			newObj.getEquipamento().setTipo(obj.getEquipamentoTipo());
		}
		return repo.save(newObj);
	}

	public OrdemDeServico updateStatus(@Valid StatusDaOrdemDeServico status, Integer id) {
		OrdemDeServico obj = find(id);
		validarStatus(status, obj);
		if (obj.getStatus() == StatusDaOrdemDeServico.AGUARDANDO_CLIENTE) {
			emailService.sendOrderConfirmationHtmlEmail(obj);
		}
		if (obj.getStatus() == StatusDaOrdemDeServico.CONCLUIDA) {
			emailService.sendOrderConclusionHtmlEmail(obj);
		}
		return repo.save(obj);
	}
	
	public OrdemDeServico validarStatus(StatusDaOrdemDeServico status, OrdemDeServico obj) {
		if (obj.getStatus() == StatusDaOrdemDeServico.PENDENTE) {
			if (status == StatusDaOrdemDeServico.AGUARDANDO_CLIENTE || status == StatusDaOrdemDeServico.PENDENTE ||
					status == StatusDaOrdemDeServico.CONCLUIDA) {
				obj.setStatus(status);
				return obj;
			} else {
				throw new DataIntegrityException("Violação de dados. Não é possível alterar o status para o valor desejado.");
			}
		}	
			
		if (obj.getStatus() == StatusDaOrdemDeServico.AGUARDANDO_CLIENTE) {
			if (status == StatusDaOrdemDeServico.APROVADA || status == StatusDaOrdemDeServico.RECUSADA || 
					status == StatusDaOrdemDeServico.AGUARDANDO_CLIENTE || status == StatusDaOrdemDeServico.CONCLUIDA) {
				obj.setStatus(status);
				return obj;
			} else {
				throw new DataIntegrityException("Violação de dados. Não é possível alterar o status para o valor desejado.");
			}
		}
		
		if (obj.getStatus() == StatusDaOrdemDeServico.APROVADA || obj.getStatus() == StatusDaOrdemDeServico.RECUSADA) {
			if (status == StatusDaOrdemDeServico.CONCLUIDA || status == StatusDaOrdemDeServico.APROVADA ||
					status == StatusDaOrdemDeServico.RECUSADA) {
				obj.setStatus(status);
				return obj;
			} else {
				throw new DataIntegrityException("Violação de dados. Não é possível alterar o status para o valor desejado.");
			} 
		}
		
		if (obj.getStatus() == StatusDaOrdemDeServico.CONCLUIDA) {
			obj.setStatus(status);
			return obj;
		} else {
			throw new DataIntegrityException("Violação de dados. Não é possível alterar o status para o valor desejado.");
		} 
	}
	
	public OrdemDeServico updateStatusToAprovada(Integer id) {
		OrdemDeServico obj = find(id);
		validarStatus(StatusDaOrdemDeServico.APROVADA, obj);
		return repo.save(obj);
	}
	
	public OrdemDeServico updateStatusToRecusada(Integer id) {
		OrdemDeServico obj = find(id);
		validarStatus(StatusDaOrdemDeServico.RECUSADA, obj);
		return repo.save(obj);
	}
	
	public URI uploadAvariaImage(MultipartFile multipartFile, Integer id) {
		OrdemDeServico obj = find(id);
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefixName + obj.getId() + ".jpg";
		obj.setImageName(fileName);
		repo.save(obj);
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
