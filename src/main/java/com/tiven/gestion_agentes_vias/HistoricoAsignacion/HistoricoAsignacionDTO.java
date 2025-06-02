package com.tiven.gestion_agentes_vias.HistoricoAsignacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoAsignacionDTO {
    private UUID id;
    private UUID idAgente;
    private String codigoAgente;
    private String nombreAgente;
    private UUID idVia;
    private String tipoVia;
    private String esCalleOCarreraVia;
    private Integer numeroVia;
    private Date fechaAsignacion;
    private String usuarioAsignador;
}
