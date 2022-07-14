package com.sofka.cicilismo.usecases.equipousecase;

import com.sofka.cicilismo.mappers.MapperEquipo;
import com.sofka.cicilismo.models.EquipoDTO;
import com.sofka.cicilismo.repository.EquipoRepository;
import com.sofka.cicilismo.repository.GuardarEquipo;
import com.sofka.cicilismo.service.SequenceGeneradorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import static com.sofka.cicilismo.collection.Equipo.SEQUENCE_EQUIPO;


@Service
@Validated
public class CrearEquipoUseCase implements GuardarEquipo {

    private final EquipoRepository equipoRepository;

    private final MapperEquipo mapperEquipo;

    private final SequenceGeneradorService service;

    public CrearEquipoUseCase(EquipoRepository equipoRepository, MapperEquipo mapperEquipo, SequenceGeneradorService service) {
        this.equipoRepository = equipoRepository;
        this.mapperEquipo = mapperEquipo;
        this.service = service;
    }

    @Override
    public Mono<EquipoDTO> apply(EquipoDTO nuevoEquipo) {
        return service.getSequenceNumber(SEQUENCE_EQUIPO).flatMap(id->{
            nuevoEquipo.setId(id.intValue());
            return equipoRepository.
                    save(mapperEquipo.equipoDTOAEquipo(null).apply(nuevoEquipo))
                    .thenReturn(nuevoEquipo);
        });
    }
}
