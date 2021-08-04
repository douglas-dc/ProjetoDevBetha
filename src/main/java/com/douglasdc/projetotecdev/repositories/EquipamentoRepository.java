package com.douglasdc.projetotecdev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglasdc.projetotecdev.domain.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {

}
