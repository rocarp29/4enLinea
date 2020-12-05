package com.testing.cuatroEnLinea.repository;

import com.testing.cuatroEnLinea.model.PartidoLiga;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository

public interface LigaRepository extends JpaRepository<PartidoLiga, Long>{
    
    

}
