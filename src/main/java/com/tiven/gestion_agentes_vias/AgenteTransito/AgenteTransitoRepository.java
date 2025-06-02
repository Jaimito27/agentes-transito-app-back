package com.tiven.gestion_agentes_vias.AgenteTransito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgenteTransitoRepository extends JpaRepository<AgenteTransito, UUID> {
    //Metodos personalizados
    Optional<AgenteTransito> findByCodigo(String codigo);
}

