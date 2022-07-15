package com.sofka.cicilismo.equipousecase;

import com.sofka.cicilismo.collection.Equipo;
import com.sofka.cicilismo.mappers.MapperEquipo;
import com.sofka.cicilismo.models.EquipoDTO;
import com.sofka.cicilismo.repository.EquipoRepository;
import com.sofka.cicilismo.usecases.equipousecase.ObtenerEquiposUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObtenerEquiposUseCaseTest {

    EquipoRepository equipoRepository;

    ObtenerEquiposUseCase obtenerEquiposUseCase;

    @BeforeEach
    public void setUp(){
        MapperEquipo mapperEquipo = new MapperEquipo();
        equipoRepository = mock(EquipoRepository.class);
        obtenerEquiposUseCase = new ObtenerEquiposUseCase(equipoRepository,mapperEquipo);
    }

    @Test
    void getValidationCaseTest(){
        var equipo = new Equipo();
        equipo.setId(1);
        equipo.setNombreEquipo("MOVISTAR TEAM");
        equipo.setCodigoEquipo("MT1");
        equipo.setPais("españa");

        when(equipoRepository.findAll()).thenReturn(Flux.just(equipo));

        StepVerifier.create(obtenerEquiposUseCase.get())
                .expectNextMatches(team -> {
                    assert team.getId().equals(1);
                    assert team.getNombreEquipo().equals("MOVISTAR TEAM");
                    assert team.getCodigoEquipo().equals("MT1");
                    assert team.getPais().equals("españa");
                    return true;
                }).expectComplete().verify();

        verify(equipoRepository).findAll();
    }


}