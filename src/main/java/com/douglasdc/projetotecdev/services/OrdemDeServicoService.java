package com.douglasdc.projetotecdev.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douglasdc.projetotecdev.domain.Equipamento;
import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;
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
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		repo.save(obj);
		for (Equipamento equip : obj.getEquipamentos()) {
			equip.setOrdemDeServico(obj);
			equipamentoRepository.save(equip);
		}
		return obj;
	}

	public List<OrdemDeServico> findAll() {
		return repo.findAll();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}

	public OrdemDeServico update(OrdemDeServico obj, Integer id) {
		OrdemDeServico newObj = find(id);
		if (obj.getInstante() != null) {
			newObj.setInstante(obj.getInstante());
		}
		
		if (obj.getCliente() != null) {
			newObj.setCliente(clienteService.find(obj.getCliente().getId()));
		}
		
		if (obj.getStatus() != null) {
			validarStatus(obj.getStatus(), newObj);
			if (newObj.getStatus() == StatusDaOrdemDeServico.AGUARDANDO_CLIENTE) {
				emailService.sendOrderConfirmationHtmlEmail(newObj);
			}
			if (newObj.getStatus() == StatusDaOrdemDeServico.CONCLUIDA) {
				emailService.sendOrderConclusionHtmlEmail(newObj);
			}
		}
		
		if (obj.getEquipamentos() != null) {
			for (Equipamento equipNew : newObj.getEquipamentos()) {
				for (Equipamento equipObj : obj.getEquipamentos()) {
					if (equipNew.getId() == equipObj.getId()) {
						if (!equipObj.getDescricao().isBlank() || (!equipObj.getTipo().isBlank())) {
							throw new DataIntegrityException("Erro: insira um equipamento válido");
						} else {
							equipNew.setDescricao(equipObj.getDescricao());
							equipNew.setTipo(equipObj.getTipo());
						}
						equipamentoRepository.save(equipNew);
					}
				}
			}
		}
		return repo.save(newObj);
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
