package com.tiven.gestion_agentes_vias.Vias;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tiven.gestion_agentes_vias.AgenteTransito.AgenteTransito;
import com.tiven.gestion_agentes_vias.HistoricoAsignacion.HistoricoAsignacion;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Via {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "es_calle_o_carrera", nullable = false)
    private String esCalleOcarrera;

    @Column(nullable = false)
    private int numero;

    @Column(name = "nivel_congestion", nullable = false, precision = 5, scale = 2)
    private BigDecimal nivelCongestion;

    //Relación bidireccional para AGentes de transito - Agentes actuales en esta via
    @OneToMany(mappedBy = "viaActual", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AgenteTransito> agentesEnEstaVia = new ArrayList<>();

    //Relacion bidireccional One-to-many con Historico Asignacion (Historico de asignaciones en la via)
    @OneToMany(mappedBy = "viaAsignada", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HistoricoAsignacion> historialAgentesEnEstaVia = new ArrayList<>();
}
