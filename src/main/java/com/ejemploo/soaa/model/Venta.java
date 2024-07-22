package com.ejemploo.soaa.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Venta {
    int id_venta;
    String fecha;
    int cantidad_venta;
    BigDecimal total_pagar;
    int estado;
    int id_cliente;
    int id_empleado;
    int id_producto;
    private String clienteNombre;
    private String ruc;
    private String empleadoNombre;
    private String productoNombre;


}
