package com.sofka.cicilismo.routers;


import com.sofka.cicilismo.models.EquipoDTO;
import com.sofka.cicilismo.usecases.ciclistausecase.CrearCiclistaUseCase;
import com.sofka.cicilismo.usecases.equipousecase.*;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
@Configuration
public class EquipoRouter {

    @Bean
    @RouterOperation(beanClass = CrearEquipoUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "EquipoDTO", summary = "Crear Equipo", tags = {"Equipo"}))
    public RouterFunction<ServerResponse> crearEquipo(CrearEquipoUseCase useCase) {
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
    @RouterOperation(beanClass = CrearEquipoUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "EquipoDTO", summary = "Actualizar Equipo", tags = {"Equipo"},
                    parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Integer"),
                            @Parameter(in = ParameterIn.PATH, name = "nombreEquipo", description = "String"),
                            @Parameter(in = ParameterIn.PATH, name = "codigoEquipo", description = "String"),
                            @Parameter(in = ParameterIn.PATH, name = "pais", description = "String")})
    )
    public RouterFunction<ServerResponse> actualizarEquipo(ActualizarEquipoPorIdUseCase useCase) {
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
    @RouterOperation(beanClass = CrearEquipoUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "Eliminar Equipo", summary = "Eliminar equipo", tags = {"Equipo"},
            parameters = @Parameter(in = ParameterIn.PATH, name = "id",description = "Integer")))
    public RouterFunction<ServerResponse> eliminarEquipo(EliminarEquipoUseCase useCase) {
        return route(
                DELETE("/eliminarequipo/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")), void.class))

        );
    }

    @Bean
    @RouterOperation(beanClass = CrearEquipoUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "Obtener todas los Equipo", summary = "Obtener todas los Equipos", tags = {"Equipo"}))
    public RouterFunction<ServerResponse> obtenerEquipos(ObtenerEquiposUseCase useCase) {
        return route(
                GET("/obtenerEquipos").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), EquipoDTO.class))
        );
    }

    @Bean
    @RouterOperation(beanClass = CrearEquipoUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "Obtener Equipo por id", summary = "Obtener Equipo por id", tags = {"Equipo"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Integer")}))
    public RouterFunction<ServerResponse> obtenerEquipoPorId(ObtenerEquipoPorIdUseCase useCase) {
        return route(
                GET("/obtenerequipo/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.pathVariable("id")),
                                EquipoDTO.class))
        );
    }


}
