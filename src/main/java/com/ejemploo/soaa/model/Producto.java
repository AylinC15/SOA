package com.ejemploo.soaa.model;

import lombok.Data;

@Data
public class Producto {

    int id_producto;
    String name;
    String marca;
    int cantidad;
    double precio;
    int estado;

    int id_proveedor;
    private String nombre_proveedor;
}
