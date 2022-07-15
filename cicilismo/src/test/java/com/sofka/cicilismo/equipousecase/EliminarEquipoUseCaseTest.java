package com.sofka.cicilismo.equipousecase;

import com.sofka.cicilismo.collection.Equipo;
import com.sofka.cicilismo.mappers.MapperEquipo;
import com.sofka.cicilismo.repository.EquipoRepository;
import com.sofka.cicilismo.usecases.equipousecase.EliminarEquipoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class EliminarEquipoUseCaseTest {

    @Mock
    EquipoRepository equipoRepository;

    @Mock
    EliminarEquipoUseCase eliminarEquipoUseCase;

    MapperEquipo mapperEquipo = new MapperEquipo();

    @BeforeEach
    public void setUp() {
        equipoRepository = mock(EquipoRepository.class);
        eliminarEquipoUseCase = new EliminarEquipoUseCase(equipoRepository, mapperEquipo);
    }

    @Test
    void getValidationDeleteUseCaseTest() {
        var equipo = new Equipo();
        equipo.setId(1);
        equipo.setNombreEquipo("MOVISTAR TEAM");
        equipo.setCodigoEquipo("MT1");
        equipo.setPais("españa");

        Mono.just(equipo).flatMap(equipoRepository::save);

        when(equipoRepository.findById(1)).thenReturn(Mono.just(equipo));
        when(equipoRepository.delete(equipo)).thenReturn(Mono.empty());
        StepVerifier.create(eliminarEquipoUseCase.apply("1"))
                .expectNextMatches(team -> {
                    assert team.equals("");
                    return true;
                })
                .expectComplete();

        verify(equipoRepository, times(1)).findById(1);
    }


}