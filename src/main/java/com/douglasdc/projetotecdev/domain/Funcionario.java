package com.douglasdc.projetotecdev.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.douglasdc.projetotecdev.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message="Preenchimento de nome obrigatório")
	@Length(min=3, max=80, message="O tamanho deve ser entre 3 e 80 caracteres")
	private String nome;
	
	@NotBlank(message="Preenchimento de email obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	@NotBlank(message="Preenchimento de senha obrigatório")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String senha;
	
	private Integer perfil;
	
	public Funcionario() {
		
	}
	
	public Funcionario(Integer id, String nome, String email, String senha, Perfil perfil) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.perfil = (perfil == null) ? null : perfil.getCod();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return Perfil.toEnum(perfil);
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil.getCod();
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
		Funcionario other = (Funcionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
