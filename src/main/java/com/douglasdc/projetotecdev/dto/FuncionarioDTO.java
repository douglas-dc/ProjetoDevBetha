package com.douglasdc.projetotecdev.dto;

import java.io.Serializable;

import com.douglasdc.projetotecdev.domain.Funcionario;
import com.douglasdc.projetotecdev.domain.enums.Perfil;

public class FuncionarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	private Integer perfil;
	
	public FuncionarioDTO() {
		
	}
	
	public FuncionarioDTO(Funcionario obj) {
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.perfil = obj.getPerfil().getCod();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Perfil getPerfil() {
		return Perfil.toEnum(perfil);
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil.getCod();
	}
}
