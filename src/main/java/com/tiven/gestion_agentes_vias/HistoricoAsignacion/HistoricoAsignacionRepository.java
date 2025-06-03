package com.tiven.gestion_agentes_vias.HistoricoAsignacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoricoAsignacionRepository extends JpaRepository<HistoricoAsignacion, UUID> {
    //Metodos personalizados
    List<HistoricoAsignacion> findByAgenteTransito_Codigo(String  codigoAgente);
    List<HistoricoAsignacion> findByViaAsignada_EsCalleOCarreraAndViaAsignada_Numero(String esCalleOCarrera, Integer numero);

    List<HistoricoAsignacion> findByAgenteTransito_CodigoAndViaAsignada_EsCalleOCarreraAndViaAsignada_Numero(String codigoAgente, String esCalleOCarrera, Integer numero);
}
