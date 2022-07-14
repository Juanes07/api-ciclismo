package com.sofka.cicilismo.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EquipoDTO {

    private Integer id;

    @NotBlank(message = "El equipo debe tener un nombre")
    private String nombreEquipo;

    @Length(max=3, message = "el codigo del equipo debe ser maximo 3 caracteres")
    @NotBlank(message = "El codigo del equipo es requerido")
    private String codigoEquipo;

    @NotBlank(message = "El equipo debe tener un pais asociado")
    private String pais;

    @Override
    public String toString() {
        return "EquipoDTO{" +
                "id=" + id +
                ", nombreEquipo='" + nombreEquipo + '\'' +
                ", codigoEquipo='" + codigoEquipo + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
