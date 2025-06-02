package com.tiven.gestion_agentes_vias.AgenteTransito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenteTransitoRequestDTO {
    private String nombreCompleto;
    private String codigo;
    private BigDecimal anosExperiencia;
    private String codigoSecretariaTransito;
    private UUID idViaActual;
}
