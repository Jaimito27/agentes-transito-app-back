package com.tiven.gestion_agentes_vias.AgenteTransito;

import com.tiven.gestion_agentes_vias.HistoricoAsignacion.HistoricoAsignacion;
import com.tiven.gestion_agentes_vias.HistoricoAsignacion.HistoricoAsignacionRepository;
import com.tiven.gestion_agentes_vias.Vias.Via;
import com.tiven.gestion_agentes_vias.Vias.ViaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgenteTransitoService {

    @Autowired
    private AgenteTransitoRepository agenteTransitoRepository;

    @Autowired
    private ViaService viaService; //obtener la entidad Via

    @Autowired
    private HistoricoAsignacionRepository historicoAsignacionRepository;

    //mapear entidad a DTO
    private AgenteTransitoDTO convertToDto(AgenteTransito agente) {
        AgenteTransitoDTO dto = new AgenteTransitoDTO();
        dto.setId(agente.getId());
        dto.setNombreCompleto(agente.getNombreCompleto());
        dto.setCodigo(agente.getCodigo());
        dto.setAnosExperiencia(agente.getAnosExperiencia());
        dto.setCodigoSecretariaTransito(agente.getCodigoSecretariaTransito());
        if(agente.getViaActual() != null) {
            dto.setIdViaActual(agente.getViaActual().getId());
            dto.setNombreViaActual(agente.getViaActual().getEsCalleOcarrera() + " " + agente.getViaActual().getNumero());
        }
        return dto;
    }

    //mapear de dto a entidad (para la creacion y/o actualizacion
    private AgenteTransito convertToEntity(AgenteTransitoRequestDTO agenteRequestDTO){
        AgenteTransito agente = new AgenteTransito();

        agente.setNombreCompleto(agenteRequestDTO.getNombreCompleto());
        agente.setCodigo(agenteRequestDTO.getCodigo());
        agente.setAnosExperiencia(agenteRequestDTO.getAnosExperiencia());
        agente.setCodigoSecretariaTransito(agenteRequestDTO.getCodigoSecretariaTransito());
        return agente;
    }

    public List<AgenteTransitoDTO> getAllAgentes(){
        return agenteTransitoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AgenteTransitoDTO getAgenteById(UUID id){
        AgenteTransito agente = agenteTransitoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agente no encontrado con ID: "+id));
        return convertToDto(agente);
    }

    @Transactional // asegurs que ambas operaciones (guardar agente e histórico) se completeno fallen juntas
    public AgenteTransitoDTO createAgente(AgenteTransitoRequestDTO agenteRequestDTO){
        //validar que el codigo no exista
        if(agenteTransitoRepository.findByCodigo(agenteRequestDTO.getCodigo()).isPresent()){
            throw new BadRequestException("El codigo de agente " + agenteRequestDTO.getCodigo() + " ya existe");
        }

        AgenteTransito agente = convertToEntity(agenteRequestDTO);

        //si se proporciona una via inicial, se asigna
        if(agenteRequestDTO.getIdViaActual() != null){
            Via via = viaService.getViaEntityById(agenteRequestDTO.getIdViaActual());
            if(via.getNivelCongestion().compareTo(new BigDecimal("30.0")) < 0){
                throw new BadRequestException("No se puede asignar a una vía con nivel de congestión menor a 30.");
            }
            agente.setViaActual(via);
        }
        AgenteTransito savedAgente = agenteTransitoRepository.save(agente);

        //Rehistrar en el histórico si se asignó una via iniciL

        if(savedAgente.getViaActual() != null){
            HistoricoAsignacion historico = new HistoricoAsignacion();
            historico.setAgenteTransito(savedAgente);
            historico.setViaAsignada(savedAgente.getViaActual());
            historico.setFechaAsignacion(new Date());
            historico.setUsuarioAsignador("Sistema");
            historicoAsignacionRepository.save(historico);
        }
        return convertToDto(savedAgente);
    }

    @Transactional
    public AgenteTransitoDTO updateAgente(UUID id, AgenteTransitoRequestDTO agenteRequestDTO){
        AgenteTransito existingAgente = agenteTransitoRepository.findById(id).orElseThrow(() -> ResourceNotFoundException("Agente no encontrado con Id: "+ id));

        //Validar cambio de codigo (si el nuevo codigo es diferente o es el mismo)
        if(!existingAgente.getCodigo().equals(agenteRequestDTO.getCodigo()) && agenteTransitoRepository.findByCodigo(agenteRequestDTO.getCodigo()).isPresent()){
            throw new BadRequestException("El nuevo código de agente "+agenteRequestDTO.getCodigo() + " ya existe.");
        }
        existingAgente.setNombreCompleto(agenteRequestDTO.getNombreCompleto());
        existingAgente.setCodigo(agenteRequestDTO.getCodigo());
        existingAgente.setAnosExperiencia(agenteRequestDTO.getAnosExperiencia());
        existingAgente.setCodigoSecretariaTransito(agenteRequestDTO.getCodigoSecretariaTransito());

        //manejar la asignación de vía y el histórico
        if(agenteRequestDTO.getIdViaActual() != null){
            Via nuevaVia = viaService.getViaEntityById(agenteRequestDTO.getIdViaActual());

            //validar el nivel de congestion
            if(nuevaVia.getNivelCongestion().compareTo(new BigDecimal("30.0")) < 0){
                throw new BadRequestException("No se puede asignar a una vpia con nivel de congestión menor a 30.");
            }

            //Registar en histórico si la vía actual ha cambiado
            if(existingAgente.getViaActual() == null || !existingAgente.getViaActual().getId().equals(nuevaVia.getId())){
                HistoricoAsignacion historico = new HistoricoAsignacion();
                historico.setAgenteTransito(existingAgente);
                historico.setViaAsignada(nuevaVia);
                historico.setFechaAsignacion(new Date());
                historico.setUsuarioAsignador("Sistema"); //o usuario logueado
                historicoAsignacionRepository.save(historico);
            }
            existingAgente.setViaActual(nuevaVia);
        }else {
            //si idViaActual es null, significa que se esta desasignando la via
            if(existingAgente.getViaActual() != null){
                existingAgente.setViaActual(null);
            }
        }

        AgenteTransito updatedAgente = agenteTransitoRepository.save(existingAgente);
        return convertToDto(updatedAgente);
    }

    public void deleteAgente(UUID id){
        if(!agenteTransitoRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Agente no encontrada con Id: "+ id);
        }
        // Opcional: considerar si quieres borrar el histórico de asignaciones al borrar el agente.
        // Si tienes cascade = CascadeType.ALL en OneToMany en AgenteTransito, se borrará automáticamente.
        agenteTransitoRepository.deleteById(id);
    }
}
