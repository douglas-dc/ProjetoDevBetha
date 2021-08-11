package com.douglasdc.projetotecdev.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class OrdemDeServicoDTOPut implements Serializable {
	private static final long serialVersionUID = 1L;

	private LocalDate instante;
	private String equipamentoTipo;
	private String equipamentoDescricao;
	private Integer clienteId;
	
	public OrdemDeServicoDTOPut() {
		
	}

	public OrdemDeServicoDTOPut(LocalDate instante, String equipamentoTipo, String equipamentoDescricao,
			Integer clienteId) {
		this.instante = instante;
		this.equipamentoTipo = equipamentoTipo;
		this.equipamentoDescricao = equipamentoDescricao;
		this.clienteId = clienteId;
	}


	public LocalDate getInstante() {
		return instante;
	}

	public void setInstante(LocalDate instante) {
		this.instante = instante;
	}

	public String getEquipamentoTipo() {
		return equipamentoTipo;
	}

	public void setEquipamentoTipo(String equipamentoTipo) {
		this.equipamentoTipo = equipamentoTipo;
	}

	public String getEquipamentoDescricao() {
		return equipamentoDescricao;
	}

	public void setEquipamentoDescricao(String equipamentoDescricao) {
		this.equipamentoDescricao = equipamentoDescricao;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}
}
