package com.tiven.gestion_agentes_vias.HistoricoAsignacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
public class HistoricoAsignacionController {

    @Autowired
    private HistoricoAsignacionService historicoAsignacionService;

    @GetMapping
    public ResponseEntity<List<HistoricoAsignacionDTO>> getHistorico(
            @RequestParam(required = false) String codigoAgente,
            @RequestParam(required = false) String esCalleOCarrera,
            @RequestParam(required = false) Integer numero) {
        List<HistoricoAsignacionDTO> historico;

        if(codigoAgente != null && !codigoAgente.isEmpty()){
            historico = historicoAsignacionService.getHistoricoByAgenteCodigo(codigoAgente);
        }else if(esCalleOCarrera != null && numero != null){
            historico = historicoAsignacionService.getHistoricoByVia(esCalleOCarrera, numero);
        }else{
            historico = historicoAsignacionService.getAllHistorico();
        }
        return ResponseEntity.ok(historico);
    }
}
