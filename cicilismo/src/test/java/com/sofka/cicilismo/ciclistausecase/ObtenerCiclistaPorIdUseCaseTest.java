package com.sofka.cicilismo.ciclistausecase;

import com.sofka.cicilismo.collection.Ciclista;
import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.models.CiclistaDTO;
import com.sofka.cicilismo.repository.CiclistaRepository;
import com.sofka.cicilismo.usecases.ciclistausecase.ObtenerCiclistaPorIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObtenerCiclistaPorIdUseCaseTest {

    CiclistaRepository ciclistaRepository;

    ObtenerCiclistaPorIdUseCase obtenerCiclistaPorIdUseCase;

    @BeforeEach
    public void setUp(){
        MapperCiclista mapperCiclista = new MapperCiclista();
        ciclistaRepository = mock(CiclistaRepository.class);
        obtenerCiclistaPorIdUseCase = new ObtenerCiclistaPorIdUseCase(ciclistaRepository,mapperCiclista);
    }

    @Test
    void getValidationCaseTest(){
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
        ciclistaDto.setNumeroCompetidor(ciclista.getNumeroCompetidor());
        ciclistaDto.setNacionalidad(ciclista.getNacionalidad());

        when(ciclistaRepository.findById(1)).thenReturn(Mono.just(ciclista));

        StepVerifier.create(obtenerCiclistaPorIdUseCase.apply("1"))
                .expectNextMatches(competidor -> {
                    assert ciclistaDto.getId().equals(competidor.getId());
                    assert ciclistaDto.getIdEquipo().equals(competidor.getIdEquipo());
                    assert ciclistaDto.getNombreCiclista().equals(competidor.getNombreCiclista());
                    assert ciclistaDto.getNumeroCompetidor().equals(competidor.getNumeroCompetidor());
                    assert ciclistaDto.getNacionalidad().equals(competidor.getNacionalidad());
                    return true;
                }).expectComplete();

        verify(ciclistaRepository).findById(ciclistaDto.getId());
    }

}