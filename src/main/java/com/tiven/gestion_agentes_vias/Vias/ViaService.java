package com.tiven.gestion_agentes_vias.Vias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ViaService {

    @Autowired
    private ViaRepository viaRepository;

    //mapear entidad a DTO
    private ViaDTO convertToDto(Via via){
        return new ViaDTO(via.getId(), via.getTipo(), via.getEsCalleOcarrera(), via.getNumero(), via.getNivelCongestion());
    }

    //mapear de dto a entidad (para crear y/o actualizar)
    private Via convertToEntity(ViaRequestDTO viaRequestDTO){
        Via via = new Via();
        via.setTipo(viaRequestDTO.getTipo());
        via.setEsCalleOcarrera(viaRequestDTO.getEsCalleOCarrera());
        via.setNumero(viaRequestDTO.getNumero());
        via.setNivelCongestion(viaRequestDTO.getNivelCongestion());
        return via;
    }

    public List<ViaDTO> getAllVias(){
        return viaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ViaDTO getViaById(UUID id){
        Via via = viaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Via no encontrada con Id: "+ id));
        return convertToDto(via);
    }

    public ViaDTO createVia(ViaRequestDTO viaRequestDTO){
        Via via = convertToEntity(viaRequestDTO);
        Via savedVia= viaRepository.save(via);
        return convertToDto(savedVia);
    }

    public ViaDTO updateVia(UUID id, ViaRequestDTO viaRequestDTO){
        Via existingVia = viaRepository.findById(id).orElseThrow(() -> ResourceNotFoundException("Via no encontrada con Id: "+ id));

        existingVia.setTipo(viaRequestDTO.getTipo());
        existingVia.setEsCalleOcarrera(viaRequestDTO.getEsCalleOCarrera());
        existingVia.setNumero(viaRequestDTO.getNumero());
        existingVia.setNivelCongestion(viaRequestDTO.getNivelCongestion());

        Via updatedVia= viaRepository.save(existingVia);
        return convertToDto(updatedVia);
    }

    public void deleteVia(UUID id){
        if(!viaRepository.existsById(id)){
            throw new ResourceNotFoundException("Via no encontrada con Id: "+ id);
        }
        viaRepository.deleteById(id);
    }

    //obtener una entidad via
    public Via getViaEntityById(UUID id){
        return viaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Via no encontrada con Id: "+ id));
    }
}
