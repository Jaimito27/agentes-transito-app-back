package com.tiven.gestion_agentes_vias.HistoricoAsignacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoricoAsignacionRepository extends JpaRepository<HistoricoAsignacion, UUID> {
    //Metodos personalizados
}
