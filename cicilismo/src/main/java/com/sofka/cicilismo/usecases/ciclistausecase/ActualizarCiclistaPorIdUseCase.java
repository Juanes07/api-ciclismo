package com.sofka.cicilismo.usecases.ciclistausecase;


import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.models.CiclistaDTO;
import com.sofka.cicilismo.repository.CiclistaRepository;
import com.sofka.cicilismo.repository.GuardarCiclista;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class ActualizarCiclistaPorIdUseCase implements GuardarCiclista {

    private final CiclistaRepository ciclistaRepository;

    private final MapperCiclista mapperCiclista;

    public ActualizarCiclistaPorIdUseCase(CiclistaRepository ciclistaRepository, MapperCiclista mapperCiclista) {
        this.ciclistaRepository = ciclistaRepository;
        this.mapperCiclista = mapperCiclista;
    }

    @Override
    public Mono<CiclistaDTO> apply(CiclistaDTO ciclistaDTO) {
        Objects.requireNonNull(ciclistaDTO.getId(), "el id del ciclista es necesario");
        return ciclistaRepository
                .save(mapperCiclista.ciclistaDTOACiclista(ciclistaDTO.getId())
                        .apply(ciclistaDTO))
                .thenReturn(ciclistaDTO);
    }
}
