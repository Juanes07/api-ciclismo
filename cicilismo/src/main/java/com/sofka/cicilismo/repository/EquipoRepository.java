package com.sofka.cicilismo.repository;

import com.sofka.cicilismo.collection.Equipo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EquipoRepository extends ReactiveCrudRepository<Equipo, Integer> {
}
