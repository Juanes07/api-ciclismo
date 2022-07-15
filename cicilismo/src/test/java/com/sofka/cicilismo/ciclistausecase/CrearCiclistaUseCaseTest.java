package com.sofka.cicilismo.ciclistausecase;

import com.sofka.cicilismo.collection.Ciclista;
import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.models.CiclistaDTO;
import com.sofka.cicilismo.repository.CiclistaRepository;
import com.sofka.cicilismo.service.SequenceGeneradorService;
import com.sofka.cicilismo.usecases.ciclistausecase.CrearCiclistaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrearCiclistaUseCaseTest {

    @Mock
    CiclistaRepository ciclistaRepository;

    @Mock
    CrearCiclistaUseCase crearCiclistaUseCase;

    MapperCiclista mapperCiclista = new MapperCiclista();

    @Mock
    SequenceGeneradorService service;

    @BeforeEach
    public void setUp(){
        service = mock((SequenceGeneradorService.class));
        ciclistaRepository = mock(CiclistaRepository.class);
        crearCiclistaUseCase = new CrearCiclistaUseCase(ciclistaRepository,mapperCiclista,service);
    }


    @Test
    void getValidationCreateTest(){
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


        when(service.getSequenceNumber("ciclista_sequence")).thenReturn(Mono.just(1));
        when(ciclistaRepository.save(Mockito.any(Ciclista.class))).thenReturn(Mono.just(ciclista));

        StepVerifier.create(crearCiclistaUseCase.apply(ciclistaDto))
                .expectNextMatches( c->{
                    assert ciclistaDto.getId().equals(c.getId());
                    assert ciclistaDto.getIdEquipo().equals(c.getIdEquipo());
                    assert ciclistaDto.getNombreCiclista().equals(c.getNombreCiclista());
                    assert ciclistaDto.getNumeroCompetidor().equals(c.getNumeroCompetidor());
                    assert ciclistaDto.getNacionalidad().equals(c.getNacionalidad());
                    return true;
                }).expectComplete();

        verify(service).getSequenceNumber("ciclista_sequence");



    }

}