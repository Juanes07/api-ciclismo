package com.sofka.cicilismo.ciclistausecase;

import com.sofka.cicilismo.collection.Ciclista;
import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.models.CiclistaDTO;
import com.sofka.cicilismo.repository.CiclistaRepository;
import com.sofka.cicilismo.usecases.ciclistausecase.ActualizarCiclistaPorIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActualizarCiclistaPorIdUseCaseTest {

    MapperCiclista mapperCiclista;

    CiclistaRepository ciclistaRepository;

    ActualizarCiclistaPorIdUseCase actualizarCiclistaPorIdUseCase;

    @BeforeEach
    public void setUp(){
        ciclistaRepository = mock(CiclistaRepository.class);
        mapperCiclista = new MapperCiclista();
        actualizarCiclistaPorIdUseCase = new ActualizarCiclistaPorIdUseCase(ciclistaRepository,mapperCiclista);
    }

    @Test
    public void setUpCase(){
        var ciclista = new Ciclista();
        ciclista.setId(1);
        ciclista.setIdEquipo(1);
        ciclista.setNombreCiclista("alfredo");
        ciclista.setNumeroCompetidor(123);
        ciclista.setNacionalidad("colombia");

        var ciclistaDto = new CiclistaDTO();
        ciclistaDto.setId(ciclista.getId());
        ciclistaDto.setIdEquipo(ciclista.getIdEquipo());
        ciclistaDto.setNombreCiclista(ciclista.getNombreCiclista());
        ciclistaDto.setNumeroCompetidor(321);
        ciclistaDto.setNacionalidad(ciclista.getNacionalidad());


        when(ciclistaRepository.save(Mockito.any(Ciclista.class))).thenReturn(Mono.just(ciclista));

        StepVerifier.create(actualizarCiclistaPorIdUseCase.apply(ciclistaDto))
                .expectNextMatches(c->{
                    assert ciclistaDto.getId().equals(c.getId());
                    assert ciclistaDto.getIdEquipo().equals(c.getIdEquipo());
                    assert ciclistaDto.getNombreCiclista().equals(c.getNombreCiclista());
                    assert ciclistaDto.getNumeroCompetidor().equals(321);
                    assert ciclistaDto.getNacionalidad().equals(c.getNacionalidad());
                    return true;
                }).verifyComplete();
        verify(ciclistaRepository).save(Mockito.any(Ciclista.class));

    }

}