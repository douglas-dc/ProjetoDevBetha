package com.douglasdc.projetotecdev.dto;

import java.io.Serializable;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;
import com.douglasdc.projetotecdev.domain.enums.StatusDaOrdemDeServico;

public class OrdemDeServicoDTO2 implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer status;
	
	public OrdemDeServicoDTO2() {
		
	}
	
	public OrdemDeServicoDTO2(OrdemDeServico ordem) {
		status = ordem.getStatus().getCod();
	}
	
	public StatusDaOrdemDeServico getStatus() {
		return StatusDaOrdemDeServico.toEnum(status);
	}

	public void setStatus(StatusDaOrdemDeServico status) {
		this.status = status.getCod();
	}
}
