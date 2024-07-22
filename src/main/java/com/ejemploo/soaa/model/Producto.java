package com.ejemploo.soaa.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Producto {

    int id_producto;
    String name;
    String marca;
    int cantidad;
    BigDecimal precio;
    int estado;

    int id_proveedor;
    private String nombre_proveedor;
}
