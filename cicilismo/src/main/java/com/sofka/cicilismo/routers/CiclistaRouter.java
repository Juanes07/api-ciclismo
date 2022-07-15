package com.sofka.cicilismo.routers;

import com.sofka.cicilismo.models.CiclistaDTO;
import com.sofka.cicilismo.usecases.ciclistausecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.annotations.RouterOperation;
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
    @RouterOperation(beanClass = CrearCiclistaUseCase.class, beanMethod = "apply",
    operation = @Operation(operationId = "CiclistaDto", summary = "crear ciclista"))
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
    @RouterOperation(beanClass = CrearCiclistaUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "CiclistaDto", summary = "Actualizar Ciclista",
                    parameters ={
                            @Parameter(in = ParameterIn.PATH, name = "id", description = "Integer"),
                            @Parameter(in = ParameterIn.PATH, name = "idEquipo", description = "Integer"),
                            @Parameter(in = ParameterIn.PATH, name = "nombreCiclista",description = "String"),
                            @Parameter(in= ParameterIn.PATH, name = "numeroCompetidor", description = "Integer"),
                            @Parameter(in = ParameterIn.PATH, name = "nacionalidad",description = "String")
                    }))
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
    @RouterOperation(beanClass = CrearCiclistaUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "obtener ciclista por su ID", summary = "Obtener ciclista por su ID",
            parameters = @Parameter(in = ParameterIn.PATH, name = "id",description = "Integer")))
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
    @RouterOperation(beanClass = CrearCiclistaUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "obtener ciclista por el id de su equipo", summary = "Obtener ciclista por el id de su equipo"))
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
    @RouterOperation(beanClass = CrearCiclistaUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "Eliminar ciclista", summary = "Eliminar ciclista",
                    parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Integer")}))
    public RouterFunction<ServerResponse> eliminarCiclistaPorId(EliminarCiclistaPorIdUseCase useCase){
        return route(
                DELETE("/eliminarciclista/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")),void.class))
        );
    }



    @Bean
    @RouterOperation(beanClass = CrearCiclistaUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "obtener lista de ciclistas", summary = "Obtener lista de ciclistas"))
    public RouterFunction<ServerResponse> obtenerCiclistas(ObtenerCiclistasUseCase useCase){
        return route(
                GET("/obtenerciclistas").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), CiclistaDTO.class))
        );
    }
}
