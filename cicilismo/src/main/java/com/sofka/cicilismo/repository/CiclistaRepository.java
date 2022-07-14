package com.sofka.cicilismo.repository;

import com.sofka.cicilismo.collection.Ciclista;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CiclistaRepository extends ReactiveCrudRepository<Ciclista, Integer> {
}
