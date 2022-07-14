package com.sofka.cicilismo.mappers;

import com.sofka.cicilismo.collection.Ciclista;
import com.sofka.cicilismo.models.CiclistaDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperCiclista {
    public Function<CiclistaDTO, Ciclista> ciclistaDTOACiclista(Object o){
        return ciclistaDTO -> {
            var ciclista = new Ciclista();
            ciclista.setId(ciclistaDTO.getId());
            ciclista.setIdEquipo(ciclistaDTO.getIdEquipo());
            ciclista.setNombreCiclista(ciclistaDTO.getNombreCiclista());
            ciclista.setNumeroCompetidor(ciclistaDTO.getNumeroCompetidor());
            ciclista.setNacionalidad(ciclistaDTO.getNacionalidad());
            return ciclista;
        };
    }


    public Function<Ciclista, CiclistaDTO> ciclistaACiclistaDTO(){
        return ciclista -> {
            var ciclistaDTO = new CiclistaDTO();
            ciclistaDTO.setId(ciclista.getId());
            ciclistaDTO.setIdEquipo(ciclista.getIdEquipo());
            ciclistaDTO.setNombreCiclista(ciclista.getNombreCiclista());
            ciclistaDTO.setNumeroCompetidor(ciclista.getNumeroCompetidor());
            ciclistaDTO.setNacionalidad(ciclista.getNacionalidad());
            return ciclistaDTO;
        };
    }
}
