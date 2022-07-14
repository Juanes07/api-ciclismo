package com.sofka.cicilismo.router;


import com.sofka.cicilismo.models.EquipoDTO;
import com.sofka.cicilismo.usecase.equipousecase.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;


import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
@Configuration
public class EquipoRouter {

    @Bean
    public RouterFunction<ServerResponse> crearEquipo(CrearEquipoUseCase useCase){
        Function<EquipoDTO, Mono<ServerResponse>> trigger = equipoDTO -> useCase.apply(equipoDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));

        return route(
                POST("/crearequipo").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(EquipoDTO.class).flatMap(trigger)
        );
    }


    @Bean
    public RouterFunction<ServerResponse> actualizarEquipo(ActualizarEquipoPorIdUseCase useCase){
        Function<EquipoDTO, Mono<ServerResponse>> trigger = equipoDTO -> useCase.apply(equipoDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));
        return route(
                PUT("/actualizarequipo/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(EquipoDTO.class).flatMap(trigger)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> eliminarEquipo(EliminarEquipoUseCase useCase){
        return route(
                DELETE("/eliminarequipo/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")),void.class))

        );
    }

    @Bean
    public RouterFunction<ServerResponse> obtenerEquipos(ObtenerEquiposUseCase useCase){
        return route(
                GET("/obtenerEquipos").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), EquipoDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> obtenerEquipoPorId(ObtenerEquipoPorIdUseCase useCase){
        return route(
                GET("/obtenerequipo/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")),
                                EquipoDTO.class))
        );
    }



}
