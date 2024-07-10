package com.ejemploo.soaa.model;

import lombok.Data;

@Data
public class Devolucion {

    int id_devolucion;
    String fecha;
    String producto;
    int estado;
    int id_cliente;
    private String nombre;
}
