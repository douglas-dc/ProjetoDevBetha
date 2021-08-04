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
import com.douglasdc.projetotecdev.dto.OrdemDeServicoDTO;
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
	
	@Value("${img.prefix.name}")
	private String prefixName;
	
	@Value("${img.prefix.url}")
	private String prefixUrl;
	
//	public OrdemDeServico find(Integer id) {
//		Optional<OrdemDeServico> obj = repo.findById(id);
//		return obj.orElseThrow(() -> new ObjectNotFoundException(
//				"Ordem de serviço não encontrada! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName()));
//	}
	
	public OrdemDeServico find(Integer id) {
		OrdemDeServico obj = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Ordem de serviço não encontrada! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName()));
		obj.setImageName(prefixUrl + obj.getImageName());
		return obj;
	}

	public OrdemDeServico insert(OrdemDeServico obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public List<OrdemDeServico> findAll() {
		return repo.findAll();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	public OrdemDeServico fromDTO(@Valid OrdemDeServicoDTO objDto) {
		return new OrdemDeServico(objDto.getId(), objDto.getInstante(), objDto.getClienteNome(), objDto.getStatus());
	}

	public OrdemDeServico update(OrdemDeServico obj) {
		OrdemDeServico newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(OrdemDeServico newObj, OrdemDeServico obj) {
		newObj.setInstante(obj.getInstante());
		newObj.setCliente(obj.getCliente());
		newObj.setEquipamento(obj.getEquipamento());
		newObj.setStatus(obj.getStatus());
	}

	public OrdemDeServico updateStatus(@Valid StatusDaOrdemDeServico status, Integer id) {
		OrdemDeServico obj = find(id);
		validarStatus(status, obj);
		if (obj.getStatus() == StatusDaOrdemDeServico.AGUARDANDO_CLIENTE) {
			emailService.sendOrderConfirmationHtmlEmail(obj);
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

	/*public List<OrdemDeServico> findByStatusAprovadas() {
		if (repo.findAprovadas(2).isEmpty()) {
			throw new ObjectNotFoundException("Nenhuma Ordem de serviço encontrada com o seguinte parâmetro: APROVADA");
		}
		return repo.findAprovadas(2);
	}*/
	
	/*public OrdemDeServico changeStatusToAguardandoCliente(Integer id) {
		OrdemDeServico obj = find(id);
		if (!obj.equals(null)) {
			if (obj.getStatus().getCod() == 0) {
				obj.setStatus(StatusDaOrdemDeServico.AGUARDANDO_CLIENTE);
				return repo.save(obj);
			} else if (obj.getStatus().getCod() == 1) {
				return obj;
			} else {
				throw new DataIntegrityException("Violação de dados. Não é possível alterar o status para o valor desejado.");
			}
		}
		throw new ObjectNotFoundException("Ordem de serviço não encontrada! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName());
	}*/
}
