package com.sofka.cicilismo.ciclistausecase;

import com.sofka.cicilismo.collection.Ciclista;
import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.repository.CiclistaRepository;
import com.sofka.cicilismo.usecases.ciclistausecase.ObtenerCiclistasUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObtenerCiclistasUseCaseTest {

    CiclistaRepository ciclistaRepository;

    ObtenerCiclistasUseCase obtenerCiclistasUseCase;

    @BeforeEach
    public void setUp(){
        MapperCiclista mapperCiclista = new MapperCiclista();
        ciclistaRepository = mock(CiclistaRepository.class);
        obtenerCiclistasUseCase = new ObtenerCiclistasUseCase(ciclistaRepository,mapperCiclista);
    }

    @Test
    void getValidationCaseTest(){
        var ciclista = new Ciclista();
        ciclista.setId(1);
        ciclista.setIdEquipo(1);
        ciclista.setNombreCiclista("alfredo");
        ciclista.setNumeroCompetidor(123);
        ciclista.setNacionalidad("colombia");
        when(ciclistaRepository.findAll()).thenReturn(Flux.just(ciclista));

        StepVerifier.create(obtenerCiclistasUseCase.get())
                .expectNextMatches(competidor -> {
                    assert competidor.getId().equals(ciclista.getId());
                    assert competidor.getIdEquipo().equals(ciclista.getIdEquipo());
                    assert competidor.getNombreCiclista().equals(ciclista.getNombreCiclista());
                    assert competidor.getNumeroCompetidor().equals(ciclista.getNumeroCompetidor());
                    assert competidor.getNacionalidad().equals(ciclista.getNacionalidad());
                    return true;
                }).expectComplete();

        verify(ciclistaRepository).findAll();
    }

}