package com.tiven.gestion_agentes_vias.AgenteTransito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agentes")
public class AgenteTransitoController {

    @Autowired
    private AgenteTransitoService agenteTransitoService;

    @GetMapping
    public ResponseEntity<List<AgenteTransitoDTO>> getAllAgentes() {
        List<AgenteTransitoDTO> agentes = agenteTransitoService.getAllAgentes();
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenteTransitoDTO> getAgenteById(@PathVariable UUID id) {
        AgenteTransitoDTO agente = agenteTransitoService.getAgenteById(id);
        return ResponseEntity.ok(agente);
    }

    @PostMapping
    public ResponseEntity<AgenteTransitoDTO> createAgente(AgenteTransitoRequestDTO agenteTransitoRequestDTO) {
        AgenteTransitoDTO createdAgente = agenteTransitoService.createAgente(agenteTransitoRequestDTO);
        return new ResponseEntity<>(createdAgente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenteTransitoDTO> updateAgente(@PathVariable UUID id, @RequestBody AgenteTransitoRequestDTO agenteTransitoRequestDTO) {
        AgenteTransitoDTO updatedAgente = agenteTransitoService.updateAgente(id, agenteTransitoRequestDTO);
        return ResponseEntity.ok(updatedAgente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteAgente(@PathVariable UUID id) {
        agenteTransitoService.deleteAgente(id);
        return ResponseEntity.noContent().build();
    }
}
