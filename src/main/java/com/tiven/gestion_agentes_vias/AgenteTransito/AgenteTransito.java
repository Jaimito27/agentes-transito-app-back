package com.tiven.gestion_agentes_vias.AgenteTransito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tiven.gestion_agentes_vias.HistoricoAsignacion.HistoricoAsignacion;
import com.tiven.gestion_agentes_vias.Vias.Via;
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

    //Relacion con la tabla de via
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_via_actual")
    private Via viaActual;

    //Relacion bidireccional con Historico Asignación
    @OneToMany(mappedBy = "agenteTransito", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HistoricoAsignacion> historialAsignaciones = new ArrayList<>();
    
}