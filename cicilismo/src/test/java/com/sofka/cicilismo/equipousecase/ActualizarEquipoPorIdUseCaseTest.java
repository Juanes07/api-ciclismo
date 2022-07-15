package com.sofka.cicilismo.equipousecase;

import com.sofka.cicilismo.collection.Equipo;
import com.sofka.cicilismo.mappers.MapperEquipo;
import com.sofka.cicilismo.models.EquipoDTO;
import com.sofka.cicilismo.repository.EquipoRepository;
import com.sofka.cicilismo.usecases.equipousecase.ActualizarEquipoPorIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActualizarEquipoPorIdUseCaseTest {


    MapperEquipo mapperEquipo;

    EquipoRepository equipoRepository;

    ActualizarEquipoPorIdUseCase actualizarEquipoPorIdUseCase;

    @BeforeEach
    public void setUp(){
        equipoRepository = mock(EquipoRepository.class);
        mapperEquipo = new MapperEquipo();
        actualizarEquipoPorIdUseCase = new ActualizarEquipoPorIdUseCase(equipoRepository,mapperEquipo);
    }

    @Test
    public void getUpdateCaseUseTest(){
        var equipo = new Equipo();
        equipo.setId(1);
        equipo.setNombreEquipo("MOVISTAR TEAM");
        equipo.setCodigoEquipo("MT1");
        equipo.setPais("españa");

        var equipoDto = new EquipoDTO();
        equipoDto.setId(equipo.getId());
        equipoDto.setNombreEquipo("TREK-SEGAFREDO");
        equipoDto.setCodigoEquipo("TS1");
        equipoDto.setPais("USA");

        when(equipoRepository.save(Mockito.any(Equipo.class))).thenReturn(Mono.just(equipo));

        StepVerifier.create(actualizarEquipoPorIdUseCase.apply(equipoDto))
                .expectNextMatches(team->{
                    assert equipoDto.getId().equals(team.getId());
                    assert equipoDto.getNombreEquipo().equals("TREK-SEGAFREDO");
                    assert equipoDto.getCodigoEquipo().equals("TS1");
                    assert equipoDto.getPais().equals("USA");
                    return true;
                }).verifyComplete();

        verify(equipoRepository).save(Mockito.any(Equipo.class));

    }

}