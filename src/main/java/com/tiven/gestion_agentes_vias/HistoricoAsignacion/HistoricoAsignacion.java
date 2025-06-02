package com.tiven.gestion_agentes_vias.HistoricoAsignacion;

import com.tiven.gestion_agentes_vias.AgenteTransito.AgenteTransito;
import com.tiven.gestion_agentes_vias.Vias.Via;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "historico_asignaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoAsignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="fecha_asignacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion;

    @Column(name = "usuario_asignador")
    private String usuarioAsignador;

    //relacion con agente de transito
    @ManyToOne(fetch = FetchType.LAZY) //carga peresoza
    @JoinColumn(name = "id_agente_transito", nullable = false)
    private AgenteTransito agenteTransito;

    //Reación con via
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_via_asignada", nullable = false)
    private Via viaAsignada;

}
