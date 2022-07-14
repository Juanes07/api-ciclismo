package com.sofka.cicilismo.repository;

import com.sofka.cicilismo.collection.Ciclista;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CiclistaRepository extends ReactiveCrudRepository<Ciclista, Integer> {

    Flux<Ciclista> findAllByIdEquipo(Integer equipoId);
}
