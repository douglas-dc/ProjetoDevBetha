package com.douglasdc.projetotecdev.domain.enums;

public enum StatusDaOrdemDeServico {

	PENDENTE(0, "Pendente"),
	AVALIADA(1, "Avaliada"),
	APROVADA(2, "Aprovada"),
	RECUSADA(3, "Recusada");

	private int cod;
	private String descricao;
	
	private StatusDaOrdemDeServico(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static StatusDaOrdemDeServico toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for(StatusDaOrdemDeServico x : StatusDaOrdemDeServico.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
