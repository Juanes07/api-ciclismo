package com.sofka.cicilismo.usecase.ciclistausecase;


import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.models.CiclistaDTO;
import com.sofka.cicilismo.repository.CiclistaRepository;
import com.sofka.cicilismo.repository.EquipoRepository;
import com.sofka.cicilismo.repository.GuardarCiclista;
import com.sofka.cicilismo.service.SequenceGeneradorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import static com.sofka.cicilismo.collection.Ciclista.SEQUENCE_CICLISTA;

@Service
@Validated
public class CrearCiclistaUseCase implements GuardarCiclista {

    private final EquipoRepository equipoRepository;

    private final CiclistaRepository ciclistaRepository;

    private final MapperCiclista mapperCiclista;

    private final SequenceGeneradorService service;


    public CrearCiclistaUseCase(EquipoRepository equipoRepository, CiclistaRepository ciclistaRepository, MapperCiclista mapperCiclista, SequenceGeneradorService service) {
        this.equipoRepository = equipoRepository;
        this.ciclistaRepository = ciclistaRepository;
        this.mapperCiclista = mapperCiclista;
        this.service = service;
    }

    @Override
    public Mono<CiclistaDTO> apply(CiclistaDTO ciclistaDTO) {
        return service.getSequenceNumber(SEQUENCE_CICLISTA).flatMap(id->{
            ciclistaDTO.setId(id.intValue());
            return equipoRepository.findById(ciclistaDTO.getIdEquipo())
                    .flatMap(equipo -> {
                        return ciclistaRepository
                                .save(mapperCiclista.ciclistaDTOACiclista(null).apply(ciclistaDTO))
                                .thenReturn(ciclistaDTO);
                    });
        });
    }
}
