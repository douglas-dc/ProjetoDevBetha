package com.douglasdc.projetotecdev.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Equipamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	
	private String tipo;
	
	private String descricao;
	
	@OneToOne
	@JoinColumn(name="ordemDeServico_id")
	@MapsId
	private OrdemDeServico ordemDeServico;
	
	public Equipamento() {
		
	}

	public Equipamento(Integer id, String tipo, String descricao, OrdemDeServico ordemDeServico) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.ordemDeServico = ordemDeServico;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public OrdemDeServico getOrdemDeServico() {
		return ordemDeServico;
	}

	public void setOrdemDeServico(OrdemDeServico ordemDeServico) {
		this.ordemDeServico = ordemDeServico;
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
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
