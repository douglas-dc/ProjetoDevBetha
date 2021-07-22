package com.douglasdc.projetotecdev.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;

public class OrdemDeServicoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private LocalDate instante;
	private String equipamentoTipo;
	private String clienteNome;
	private Integer status;
	
	public OrdemDeServicoDTO() {
		
	}
	
	public OrdemDeServicoDTO(OrdemDeServico ordem) {
		id = ordem.getId();
		instante = ordem.getInstante();
		status = ordem.getStatus().getCod();
		equipamentoTipo = ordem.getEquipamento();
		clienteNome = ordem.getCliente();
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
	public String getEquipamentoTipo() {
		return equipamentoTipo;
	}
	public void setEquipamentoTipo(String equipamentoTipo) {
		this.equipamentoTipo = equipamentoTipo;
	}
	public String getClienteNome() {
		return clienteNome;
	}
	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}
	public StatusDaOrdemDeServico getStatus() {
		return StatusDaOrdemDeServico.toEnum(status);
	}

	public void setStatus(StatusDaOrdemDeServico status) {
		this.status = status.getCod();
	}
}
