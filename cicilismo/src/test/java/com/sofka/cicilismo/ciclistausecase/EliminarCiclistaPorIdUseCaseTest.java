package com.sofka.cicilismo.ciclistausecase;

import com.sofka.cicilismo.collection.Ciclista;
import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.repository.CiclistaRepository;
import com.sofka.cicilismo.usecases.ciclistausecase.EliminarCiclistaPorIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EliminarCiclistaPorIdUseCaseTest {

    @Mock
    CiclistaRepository ciclistaRepository;

    @Mock
    EliminarCiclistaPorIdUseCase eliminarCiclistaPorIdUseCase;

    MapperCiclista mapperCiclista;

    @BeforeEach
    public void setUp(){
        ciclistaRepository = mock(CiclistaRepository.class);
        eliminarCiclistaPorIdUseCase = new EliminarCiclistaPorIdUseCase(ciclistaRepository,mapperCiclista);
    }

    @Test
    void getValidationDeleteCaseTest(){
        var ciclista = new Ciclista();
        ciclista.setId(1);
        ciclista.setIdEquipo(1);
        ciclista.setNombreCiclista("alfredo");
        ciclista.setNumeroCompetidor(123);
        ciclista.setNacionalidad("colombia");

        Mono.just(ciclista).flatMap(ciclistaRepository::save);

        when(ciclistaRepository.findById(1)).thenReturn(Mono.just(ciclista));
        when(ciclistaRepository.delete(ciclista)).thenReturn(Mono.empty());

        StepVerifier.create(eliminarCiclistaPorIdUseCase.apply("1"))
                .expectNextMatches(c ->{
                    assert c.equals("");
                    return true;
                }).expectComplete();

        verify(ciclistaRepository, times(1)).findById(1);
    }
}