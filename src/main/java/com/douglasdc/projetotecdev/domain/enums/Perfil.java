package com.douglasdc.projetotecdev.domain.enums;

public enum Perfil {

	ADMIN(0, "ROLE_ADMIN"),
	RECEPCIONISTA(1, "ROLE_RECEPCIONISTA"),
	TECNICO(2, "ROLE_TECNICO");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {  //construtor de enum é PRIVADO
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) { //método estático pois pode rodar sem nenhum objeto instanciado
		
		if (cod == null) {
			return null;
		}
		
		for(Perfil x : Perfil.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
