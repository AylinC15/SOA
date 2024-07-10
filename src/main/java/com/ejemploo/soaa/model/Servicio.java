package com.ejemploo.soaa.model;
import lombok.Data;
@Data
public class Servicio {

    int id_servicio;
    String tipo_servicio;
    String descripcion;
    int estado;
    int id_empleado;
    private String name;
}
