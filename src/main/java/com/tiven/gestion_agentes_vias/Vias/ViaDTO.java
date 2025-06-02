package com.tiven.gestion_agentes_vias.Vias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViaDTO {
    private UUID id;
    private String tipo;
    private String esCalleOCarrera;
    private Integer numero;
    private BigDecimal nivelCongestion;
}
