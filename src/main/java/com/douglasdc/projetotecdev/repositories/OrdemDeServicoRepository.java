package com.douglasdc.projetotecdev.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;

@Repository
public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Integer> {

	@Query("SELECT obj FROM OrdemDeServico obj WHERE obj.status = :aprovada")
	public List<OrdemDeServico> findAprovadas(@Param("aprovada") Integer status);
	
}
