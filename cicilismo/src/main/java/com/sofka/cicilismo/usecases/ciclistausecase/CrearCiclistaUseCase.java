package com.sofka.cicilismo.usecases.ciclistausecase;


import com.sofka.cicilismo.mappers.MapperCiclista;
import com.sofka.cicilismo.models.CiclistaDTO;
import com.sofka.cicilismo.repository.CiclistaRepository;
import com.sofka.cicilismo.repository.EquipoRepository;
import com.sofka.cicilismo.repository.GuardarCiclista;
import com.sofka.cicilismo.service.SequenceGeneradorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import static com.sofka.cicilismo.collection.Ciclista.SEQUENCE_CICLISTA;

@Service
@Validated
public class CrearCiclistaUseCase implements GuardarCiclista {

    private final CiclistaRepository ciclistaRepository;

    private final MapperCiclista mapperCiclista;

    private final SequenceGeneradorService service;


    public CrearCiclistaUseCase( CiclistaRepository ciclistaRepository, MapperCiclista mapperCiclista, SequenceGeneradorService service) {

        this.ciclistaRepository = ciclistaRepository;
        this.mapperCiclista = mapperCiclista;
        this.service = service;
    }

    @Override
    public Mono<CiclistaDTO> apply(CiclistaDTO ciclistaDTO) {
        return service.getSequenceNumber(SEQUENCE_CICLISTA).flatMap(id-> {
            ciclistaDTO.setId(id.intValue());
            if(ciclistaDTO.getNumeroCompetidor().toString().length() <=3){
                return ciclistaRepository
                        .save(mapperCiclista.ciclistaDTOACiclista(null).apply(ciclistaDTO))
                        .thenReturn(ciclistaDTO);
            } else {
               return Mono.error(new Exception("el numero del ciclista no debe ser mayor a 3 digitos"));
            }
        });
    }
}
