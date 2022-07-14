package com.sofka.cicilismo.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ciclista")
public class Ciclista {
    @Transient
    public static final String SEQUENCE_CICLISTA = "ciclista_sequence";

    @Id
    private Integer id;

    private Integer idEquipo;

    private String nombreCiclista;

    @Indexed(unique = true)
    private Integer numeroCompetidor;

    private String nacionalidad;


    public Ciclista() {
    }


    public Ciclista(Integer id, Integer idEquipo, String nombreCiclista, Integer numeroCompetidor, String nacionalidad) {
        this.id = id;
        this.idEquipo = idEquipo;
        this.nombreCiclista = nombreCiclista;
        this.numeroCompetidor = numeroCompetidor;
        this.nacionalidad = nacionalidad;
    }
}
