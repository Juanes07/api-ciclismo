package com.sofka.cicilismo.equipousecase;

import com.sofka.cicilismo.collection.Equipo;
import com.sofka.cicilismo.mappers.MapperEquipo;
import com.sofka.cicilismo.models.EquipoDTO;
import com.sofka.cicilismo.repository.EquipoRepository;
import com.sofka.cicilismo.usecases.equipousecase.ObtenerEquipoPorIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObtenerEquipoPorIdUseCaseTest {

    EquipoRepository equipoRepository;

    ObtenerEquipoPorIdUseCase obtenerEquipoPorIdUseCase;

    @BeforeEach
    public void setUp(){
        MapperEquipo mapperEquipo = new MapperEquipo();
        equipoRepository = mock(EquipoRepository.class);
        obtenerEquipoPorIdUseCase = new ObtenerEquipoPorIdUseCase(equipoRepository,mapperEquipo);
    }

    @Test
    void getValidationTest(){
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

        when(equipoRepository.findById(1)).thenReturn(Mono.just(equipo));

        StepVerifier.create(obtenerEquipoPorIdUseCase.apply("1"))
                .expectNextMatches(team ->{
                    assert equipoDto.getId().equals(team.getId());
                    assert equipoDto.getNombreEquipo().equals(team.getNombreEquipo());
                    assert equipoDto.getCodigoEquipo().equals(team.getCodigoEquipo());
                    assert equipoDto.getPais().equals(team.getPais());
                    return true;
                }).expectComplete();

        verify(equipoRepository).findById(equipoDto.getId());
    }


}