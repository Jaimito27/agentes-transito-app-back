package com.tiven.gestion_agentes_vias.Vias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViaRequestDTO {
    private String tipo;
    private String esCalleOCarrera;
    private Integer numero;
    private BigDecimal nivelCongestion;
}
