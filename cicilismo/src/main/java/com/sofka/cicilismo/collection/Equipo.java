package com.sofka.cicilismo.collection;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "equipo")
public class Equipo {

    @Transient
    public static final String SEQUENCE_EQUIPO = "equipo_sequence";

    @Id
    private Integer id;

    private String nombreEquipo;

    @Indexed(unique = true)
    private String codigoEquipo;

    private String pais;


}
