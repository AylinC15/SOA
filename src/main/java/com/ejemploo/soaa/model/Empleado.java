package com.ejemploo.soaa.model;
import lombok.Data;

@Data
public class Empleado {
    int id_empleado;
    String codigo;
    String name;
    String telefono;
    String DNI;
    String direccion;
    int estado;

}
