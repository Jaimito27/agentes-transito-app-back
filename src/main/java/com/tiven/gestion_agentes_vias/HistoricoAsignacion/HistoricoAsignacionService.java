package com.tiven.gestion_agentes_vias.HistoricoAsignacion;

import com.tiven.gestion_agentes_vias.AgenteTransito.AgenteTransitoRepository;
import com.tiven.gestion_agentes_vias.Vias.ViaRepository;
import com.tiven.gestion_agentes_vias.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoAsignacionService {

    @Autowired
    private HistoricoAsignacionRepository historicoAsignacionRepository;

    @Autowired
    private AgenteTransitoRepository agenteTransitoRepository;

    @Autowired
    private ViaRepository viaRepository;

    //mapear de entidad a dto
    private HistoricoAsignacionDTO convertToDto(HistoricoAsignacion historico) {
        HistoricoAsignacionDTO dto = new HistoricoAsignacionDTO();
        dto.setId(historico.getId());
        dto.setFechaAsignacion(historico.getFechaAsignacion());
        dto.setUsuarioAsignador(historico.getUsuarioAsignador());

        if (historico.getAgenteTransito() != null) {
            dto.setIdAgente(historico.getAgenteTransito().getId());
            dto.setCodigoAgente(historico.getAgenteTransito().getCodigo());
            dto.setNombreAgente(historico.getAgenteTransito().getNombreCompleto());
        }
        if (historico.getViaAsignada() != null) {
            dto.setIdVia(historico.getViaAsignada().getId());
            dto.setTipoVia(historico.getViaAsignada().getTipo());
            dto.setEsCalleOCarreraVia(historico.getViaAsignada().getEsCalleOcarrera());
            dto.setNumeroVia(historico.getViaAsignada().getNumero());
        }
        return dto;

    }

    public List<HistoricoAsignacionDTO> getAllHistorico(){
        return historicoAsignacionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<HistoricoAsignacionDTO> getHistoricoByAgenteCodigo(String codigoAgente){
        //validar que es el agente exista antes de bscar su hisitorico
        if(agenteTransitoRepository.findByCodigo(codigoAgente).isEmpty()){
            throw new ResourceNotFoundException("Agente con código "+codigoAgente+" no existe");
        }
        return historicoAsignacionRepository.findByAgenteTransito_Codigo(codigoAgente).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<HistoricoAsignacionDTO> getHistoricoByVia(String esCalleOCarrera, Integer numero){
        //Valida su la via existe antes de buscar su historico
        if(viaRepository.findByEsCalleOCarreraAndNumero(esCalleOCarrera, numero).isEmpty()){
            throw new ResourceNotFoundException("Vía "+esCalleOCarrera+" "+numero+" no encontrada");
        }
        return historicoAsignacionRepository.findByViaAsignada_EsCalleOCarreraAndViaAsignada_Numero(esCalleOCarrera, numero).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
