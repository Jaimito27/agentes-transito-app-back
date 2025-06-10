package com.tiven.gestion_agentes_vias.Vias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vias")

public class ViaController {

    @Autowired
    private ViaService viaService;

    @GetMapping
    public ResponseEntity<List<ViaDTO>> getAllVias(){
        List<ViaDTO> vias = viaService.getAllVias();
        return ResponseEntity.ok(vias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViaDTO> getViasById(@PathVariable UUID id){
        ViaDTO via = viaService.getViaById(id);
        return ResponseEntity.ok(via);
    }

    @PostMapping
    public ResponseEntity<ViaDTO> createVia(@RequestBody ViaRequestDTO viaRequestDTO){
        ViaDTO createdVia = viaService.createVia(viaRequestDTO);
        return new ResponseEntity<>(createdVia, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViaDTO> updateVia(@PathVariable UUID id, @RequestBody ViaRequestDTO viaRequestDTO){
        ViaDTO updatedVia= viaService.updateVia(id, viaRequestDTO);
        return ResponseEntity.ok(updatedVia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVia(@PathVariable UUID id){
        viaService.deleteVia(id);
        return ResponseEntity.noContent().build();
    }
}
