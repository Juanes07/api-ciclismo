package com.sofka.cicilismo.ciclistausecase;

import com.sofka.cicilismo.collection.Ciclista;
import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.models.CiclistaDTO;
import com.sofka.cicilismo.repository.CiclistaRepository;
import com.sofka.cicilismo.usecases.ciclistausecase.ObtenerCiclistaPorIdUseCase;
import com.sofka.cicilismo.usecases.ciclistausecase.ObtenerCiclistasPorIdEquipoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ObtenerCiclistasPorIdEquipoUseCaseTest {

    CiclistaRepository ciclistaRepository;

    ObtenerCiclistasPorIdEquipoUseCase obtenerCiclistasPorIdEquipoUseCase;

    @BeforeEach
    public void setUp(){
        MapperCiclista mapperCiclista = new MapperCiclista();
        ciclistaRepository = mock(CiclistaRepository.class);
        obtenerCiclistasPorIdEquipoUseCase = new ObtenerCiclistasPorIdEquipoUseCase(ciclistaRepository,mapperCiclista);
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

        when(ciclistaRepository.findAllByIdEquipo(1)).thenReturn(Flux.just(ciclista));

        StepVerifier.create(obtenerCiclistasPorIdEquipoUseCase.apply("1"))
                .expectNextMatches(ciclistaDTO -> {
                    assert ciclistaDto.getId().equals(ciclistaDTO.getId());
                    assert ciclistaDto.getIdEquipo().equals(ciclistaDTO.getIdEquipo());
                    assert ciclistaDto.getNombreCiclista().equals(ciclistaDTO.getNombreCiclista());
                    assert ciclistaDto.getNumeroCompetidor().equals(ciclistaDTO.getNumeroCompetidor());
                    assert ciclistaDto.getNacionalidad().equals(ciclistaDTO.getNacionalidad());
                    return true;
                }).expectComplete().verify();

        verify(ciclistaRepository).findAllByIdEquipo(1);
    }

}