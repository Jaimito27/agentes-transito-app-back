package com.tiven.gestion_agentes_vias.Vias;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ViaRepository extends JpaRepository<Via, UUID> {
    //Metodos personalizados
    Optional<Via> findByEsCalleOCarreraAndNumero(String esCalleOCarrera, Integer numero);
}