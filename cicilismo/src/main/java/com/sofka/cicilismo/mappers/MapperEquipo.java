package com.sofka.cicilismo.mappers;

import com.sofka.cicilismo.collection.Equipo;
import com.sofka.cicilismo.models.EquipoDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperEquipo {

    public Function<EquipoDTO, Equipo> equipoDTOAEquipo(Object o){
        return equipoDTO -> {
            var equipo = new Equipo();
            equipo.setId(equipoDTO.getId());
            equipo.setCodigoEquipo(equipoDTO.getCodigoEquipo());
            equipo.setNombreEquipo(equipoDTO.getNombreEquipo());
            equipo.setPais(equipoDTO.getPais());
            return equipo;
        };
    }

    public Function<Equipo, EquipoDTO> equipoAEquipoDTO(Object o){
        return equipo -> {
            var equipoDto = new EquipoDTO();
            equipoDto.setId(equipo.getId());
            equipoDto.setNombreEquipo(equipo.getNombreEquipo());
            equipoDto.setCodigoEquipo(equipo.getCodigoEquipo());
            equipoDto.setPais(equipo.getPais());
            return equipoDto;
        };
    }
}
