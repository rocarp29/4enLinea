package com.testing.cuatroEnLinea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import com.testing.cuatroEnLinea.model.PartidoCopa;

@Repository
public interface CopaRepository extends JpaRepository<PartidoCopa, Long> {

}

