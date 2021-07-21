package com.douglasdc.projetotecdev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglasdc.projetotecdev.domain.OrdemDeServico;

@Repository
public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Integer> {

}
