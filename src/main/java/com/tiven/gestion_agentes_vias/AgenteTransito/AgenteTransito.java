package com.tiven.gestion_agentes_vias.AgenteTransito;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agente_transito")
@NoArgsConstructor
@AllArgsConstructor

public class AgenteTransito {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(name = "anos_experiencia", precision = 5, scale = 2)
    private BigDecimal anosExperiencia;

    @Column(name = "codigo_secretaria_transito")
    private String codigoSecretariaTransito;

    
}