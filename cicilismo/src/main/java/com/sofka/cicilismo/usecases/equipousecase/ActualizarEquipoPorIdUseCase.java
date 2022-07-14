package com.sofka.cicilismo.usecases.equipousecase;

import com.sofka.cicilismo.mappers.MapperEquipo;
import com.sofka.cicilismo.models.EquipoDTO;
import com.sofka.cicilismo.repository.EquipoRepository;
import com.sofka.cicilismo.repository.GuardarEquipo;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class ActualizarEquipoPorIdUseCase implements GuardarEquipo {

    private final EquipoRepository equipoRepository;

    private final MapperEquipo mapperEquipo;

    public ActualizarEquipoPorIdUseCase(EquipoRepository equipoRepository, MapperEquipo mapperEquipo) {
        this.equipoRepository = equipoRepository;
        this.mapperEquipo = mapperEquipo;
    }

    @Override
    public Mono<EquipoDTO> apply(EquipoDTO equipoDTO) {
        Objects.requireNonNull(equipoDTO.getId(), "el id del equipo es requerido");
        return equipoRepository
                .save(mapperEquipo.equipoDTOAEquipo(equipoDTO.getId()).apply(equipoDTO))
                .thenReturn(equipoDTO);
    }
}
