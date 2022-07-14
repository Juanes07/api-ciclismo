package com.sofka.cicilismo.usecases.ciclistausecase;

import com.sofka.cicilismo.collection.Ciclista;
import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.repository.CiclistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class EliminarCiclistaPorIdUseCase implements Function<String, Mono<Void>> {

    private final CiclistaRepository ciclistaRepository;

    private final MapperCiclista mapperCiclista;

    public EliminarCiclistaPorIdUseCase(CiclistaRepository ciclistaRepository, MapperCiclista mapperCiclista) {
        this.ciclistaRepository = ciclistaRepository;
        this.mapperCiclista = mapperCiclista;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id,"el id del ciclista es requerido");
        Mono<Ciclista> ciclista = ciclistaRepository.findById(Integer.parseInt(id));
        return ciclista.flatMap( c -> {
            return ciclistaRepository.deleteById(Integer.parseInt(id));
        });
    }
}
