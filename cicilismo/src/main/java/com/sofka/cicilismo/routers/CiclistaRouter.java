package com.sofka.cicilismo.routers;

import com.sofka.cicilismo.collection.Ciclista;
import com.sofka.cicilismo.models.CiclistaDTO;
import com.sofka.cicilismo.usecases.ciclistausecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@RestController
@Configuration
public class CiclistaRouter {

    @Bean
    public RouterFunction<ServerResponse> crearCiclista(CrearCiclistaUseCase useCase){
        Function<CiclistaDTO, Mono<ServerResponse>> trigger = ciclistaDTO -> useCase.apply(ciclistaDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));
        return route(
                POST("/crearciclista").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CiclistaDTO.class).flatMap(trigger)
        );
    }



    @Bean
    public RouterFunction<ServerResponse> actualizarCiclista(ActualizarCiclistaPorIdUseCase useCase){
        Function<CiclistaDTO, Mono<ServerResponse>> trigger = ciclistaDTO -> useCase.apply(ciclistaDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));
        return route(
                PUT("/actualizarciclista/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CiclistaDTO.class).flatMap(trigger)
        );
    }


    @Bean
    public RouterFunction<ServerResponse> obtenerCiclistaPorId(ObtenerCiclistaPorIdUseCase useCase){
        return  route(
                GET("/obtenerciclista/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                useCase.apply(request.pathVariable("id")),
                                CiclistaDTO.class
                        ))
        );
    }


    @Bean
    public RouterFunction<ServerResponse> obtenerCiclistaPorIdEquipo(ObtenerCiclistasPorIdEquipoUseCase useCase){
        return route(
                GET("/obtenerciclistaporequipo/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                useCase.apply(request.pathVariable("id")),
                                CiclistaDTO.class
                        ))
        );
    }


    @Bean
    public RouterFunction<ServerResponse> eliminarCiclistaPorId(EliminarCiclistaPorIdUseCase useCase){
        return route(
                DELETE("/eliminarciclista/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")),void.class))
        );
    }



    @Bean
    public RouterFunction<ServerResponse> obtenerCiclistas(ObtenerCiclistasUseCase useCase){
        return route(
                GET("/obtenerciclistas").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), CiclistaDTO.class))
        );
    }
}
