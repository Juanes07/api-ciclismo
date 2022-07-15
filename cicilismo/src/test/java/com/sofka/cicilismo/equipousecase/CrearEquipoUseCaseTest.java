package com.sofka.cicilismo.equipousecase;

import com.sofka.cicilismo.collection.Equipo;
import com.sofka.cicilismo.mappers.MapperEquipo;
import com.sofka.cicilismo.models.EquipoDTO;
import com.sofka.cicilismo.repository.EquipoRepository;
import com.sofka.cicilismo.service.SequenceGeneradorService;
import com.sofka.cicilismo.usecases.equipousecase.CrearEquipoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CrearEquipoUseCaseTest {


    @Mock
    EquipoRepository equipoRepository;

    @Mock
    CrearEquipoUseCase crearEquipoUseCase;

    MapperEquipo mapperEquipo;

    @Mock
    SequenceGeneradorService service;


    @BeforeEach
    public void setUp(){
        service = mock((SequenceGeneradorService.class));
        equipoRepository = mock(EquipoRepository.class);
        crearEquipoUseCase = new CrearEquipoUseCase(equipoRepository, mapperEquipo, service);
    }

    @Test
    void getValidationCreateTest(){
        var equipo = new Equipo();
        equipo.setId(1);
        equipo.setNombreEquipo("MOVISTAR TEAM");
        equipo.setCodigoEquipo("MT1");
        equipo.setPais("españa");

        var equipoDto = new EquipoDTO();
        equipoDto.setId(equipo.getId());
        equipoDto.setNombreEquipo(equipo.getNombreEquipo());
        equipoDto.setCodigoEquipo(equipo.getCodigoEquipo());
        equipoDto.setPais(equipo.getPais());

        when(service.getSequenceNumber("equipo_sequence")).thenReturn(Mono.just(1));
        when(equipoRepository.save(Mockito.any(Equipo.class))).thenReturn(Mono.just(equipo));

        StepVerifier.create(crearEquipoUseCase.apply(equipoDto))
                .expectNextMatches(team -> {
                    assert equipoDto.getId().equals(team.getId());
                    assert equipoDto.getNombreEquipo().equals(team.getNombreEquipo());
                    assert equipoDto.getCodigoEquipo().equals(team.getCodigoEquipo());
                    assert equipoDto.getPais().equals(team.getPais());
                    return true;
                }).expectComplete();

        verify(service).getSequenceNumber("equipo_sequence");


    }

}