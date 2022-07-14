package com.sofka.cicilismo.usecases.equipousecase;

import com.sofka.cicilismo.collection.Equipo;
import com.sofka.cicilismo.mappers.MapperEquipo;
import com.sofka.cicilismo.repository.EquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class EliminarEquipoUseCase implements Function<String, Mono<Void>> {

    private final EquipoRepository equipoRepository;

    private final MapperEquipo mapperEquipo;

    public EliminarEquipoUseCase(EquipoRepository equipoRepository, MapperEquipo mapperEquipo) {
        this.equipoRepository = equipoRepository;
        this.mapperEquipo = mapperEquipo;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id,"el id del equipo es requerido");
        Mono<Equipo> equipo = equipoRepository.findById(Integer.parseInt(id));
        return equipo.flatMap( e -> {
            return equipoRepository.deleteById(Integer.parseInt(id));
        });
    }
}
