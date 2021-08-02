package com.douglasdc.projetotecdev.domain;

import java.io.Serializable;
import java.time.LocalDate;

//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToOne;

import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;

@Entity
public class OrdemDeServico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private LocalDate instante;
	
	//@OneToOne(cascade=CascadeType.ALL, mappedBy="ordemDeServico")
	private String equipamento;
	
	private String cliente;
	
	private Integer status;
	
	private String imageUrl;
	
	public OrdemDeServico() {
		
	}

	public OrdemDeServico(Integer id, LocalDate instante, String equipamento, String cliente, StatusDaOrdemDeServico status) {
		super();
		this.id = id;
		this.instante = instante;
		this.equipamento = equipamento;
		this.cliente = cliente;
		this.status = (status == null) ? null : status.getCod();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getInstante() {
		return instante;
	}

	public void setInstante(LocalDate instante) {
		this.instante = instante;
	}
	
	public String getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	public StatusDaOrdemDeServico getStatus() {
		return StatusDaOrdemDeServico.toEnum(status);
	}

	public void setStatus(StatusDaOrdemDeServico status) {
		this.status = status.getCod();
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemDeServico other = (OrdemDeServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ordem de serviço número: ");
		builder.append(getId());
		builder.append(", Data: ");
		builder.append(getInstante());
		builder.append(", Cliente: ");
		builder.append(getCliente());
		builder.append(", Equipamento: ");
		builder.append(getEquipamento());
		builder.append("Deseja confirmar a realização do serviço? ");
		builder.append("http://localhost:8080/ordens/" + getId() + "/aprovada");
		builder.append(" http://localhost:8080/ordens/" + getId() + "/recusada");
		return builder.toString();
	}
}
