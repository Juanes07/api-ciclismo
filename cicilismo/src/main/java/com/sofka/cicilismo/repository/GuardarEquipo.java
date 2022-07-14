package com.sofka.cicilismo.repository;

import com.sofka.cicilismo.models.EquipoDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface GuardarEquipo {
    Mono<EquipoDTO>apply(@Valid EquipoDTO equipoDTO);
}
