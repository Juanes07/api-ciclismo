package com.sofka.cicilismo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CiclistaDTO {

    private Integer id;

    private Integer idEquipo;

    @NotBlank(message = "El ciclista debe tener un nombre")
    private String nombreCiclista;

    @Length(max = 3,message = "el codigo del ciclista debe ser maximo 3 caracteres")
    @NotBlank(message = "el codigo del ciclista no puede estar vacio")
    private Integer numeroCompetidor;

    @NotBlank(message = "el ciclista debe tener una nacionalidad")
    private String nacionalidad;

    @Override
    public String toString() {
        return "CiclistaDTO{" +
                "id=" + id +
                ", idEquipo=" + idEquipo +
                ", nombreCiclista='" + nombreCiclista + '\'' +
                ", numeroCompetidor=" + numeroCompetidor +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }
}
