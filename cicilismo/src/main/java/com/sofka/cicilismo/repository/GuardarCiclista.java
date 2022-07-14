package com.sofka.cicilismo.repository;

import com.sofka.cicilismo.models.CiclistaDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface GuardarCiclista {
    Mono<CiclistaDTO> apply(@Valid CiclistaDTO ciclistaDTO);
}
