package com.ejemploo.soaa.model;

import lombok.Data;
@Data
public class Tablero {
    int id_tablero;
    String progreso;
    int estado;
    int id_cliente;

    // Campos del cliente
    private String nombre;

}
